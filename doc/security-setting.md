# JWT, SFTP 도입 플랜 (Vue.js 연동 전제)

## 0. 개요

Spring Boot 3.3.0 기반 사내 시스템에 **자체 JWT 인증**을 우선 구현하고,  
**Vue.js 프론트엔드 (http://localhost:5173)** 와의 연동을 전제로 한다.

- 인증: 자체 JWT(Access/Refresh 토큰) → 향후 OAuth2/OIDC 확장 가능 구조
- 파일 전송: SFTP 클라이언트 도입 (배치/수동 업·다운로드 공통 활용)
- 프론트엔드: Vue.js + Axios, LocalStorage 기반 토큰 관리 (향후 HttpOnly Cookie 검토)

---

## 1. 의존성 추가 (`build.gradle`)

### 1.1 JWT 처리 (필수)

- `io.jsonwebtoken:jjwt-api`
- `io.jsonwebtoken:jjwt-impl`
- `io.jsonwebtoken:jjwt-jackson`  
  → 최신 버전 사용 (Spring Boot 3.3.0 호환 버전)

### 1.2 SFTP (필수)

- `com.jcraft:jsch`  
  → 가장 널리 사용되는 Java SFTP 라이브러리

### 1.3 OAuth2 리소스 서버 (선택적, 확장용)

- `spring-boot-starter-oauth2-resource-server`  
  → **주석 처리** 또는 별도 프로파일로 추가 (Keycloak 등 연동 시 활성화)

---

## 2. 자체 JWT 인증 구현 (우선 구현)

### 2.1 JWT 토큰 제공자

**파일 경로**

- `src/main/java/com/example/springapi/common/security/jwt/JwtTokenProvider.java`

**역할**

- JWT 토큰 생성/검증/파싱 로직
- 지원 알고리즘: `HS256` 또는 `RS256`
- Access Token, Refresh Token 각각 생성
- 토큰 만료 시간 검증 및 예외 처리

### 2.2 JWT 인증 필터

**파일 경로**

- `src/main/java/com/example/springapi/common/security/jwt/JwtAuthenticationFilter.java`

**역할**

- 요청 헤더: `Authorization: Bearer <token>` 에서 JWT 추출
- 토큰 유효성 검증
- 검증 성공 시 `Authentication` 객체 생성 → SecurityContext에 저장
- `SecurityFilterChain` 에 등록 (기본 `UsernamePasswordAuthenticationFilter` 이전에 실행)

### 2.3 인증 실패 처리

**파일 경로**

- `src/main/java/com/example/springapi/common/security/jwt/JwtAuthenticationEntryPoint.java`

**역할**

- 인증 실패 시 `401 Unauthorized` 응답
- Vue.js에서 공통 처리할 수 있도록 JSON 형식의 커스텀 에러 메시지 반환  
  (예: `{"success": false, "errorCode": "...", "message": "..."}`)

### 2.4 사용자 정보 로드 서비스

**파일 경로**

- `src/main/java/com/example/springapi/common/security/jwt/JwtUserDetailsService.java`

**역할**

- `UserDetailsService` 구현
- DB 사용자 테이블에서 사용자 조회
- 계정 상태(활성/잠금/만료 등)와 권한(ROLE) 정보 제공

### 2.5 인증 API 컨트롤러

**파일 경로**

- `src/main/java/com/example/springapi/web/auth/AuthController.java`

**엔드포인트**

- `POST /api/auth/login`
  - 사용자 로그인
  - ID/비밀번호 검증 후 Access/Refresh Token 발급
- `POST /api/auth/refresh`
  - Refresh Token으로 Access Token 재발급
- `GET /api/auth/me`
  - 현재 로그인한 사용자 정보 조회 (Vue.js에서 마이페이지/헤더 표시용)
- `POST /api/auth/logout` (선택적, 향후 구현)
  - 토큰 무효화 (Redis 블랙리스트, 토큰 버전 관리 등으로 확장 가능)

### 2.6 인증 관련 DTO

**파일 경로**

- `src/main/java/com/example/springapi/web/auth/dto/LoginRequest.java`
  - 필드: `username`, `password`
  - `@NotBlank` 등 Validation 어노테이션 추가

- `src/main/java/com/example/springapi/web/auth/dto/TokenResponse.java`
  - 필드 예시  
    - `accessToken`  
    - `refreshToken`  
    - `accessTokenExpiresIn`  
    - `refreshTokenExpiresIn`
  - Vue.js에서 LocalStorage에 그대로 저장 가능한 구조

- `src/main/java/com/example/springapi/web/auth/dto/RefreshTokenRequest.java`
  - 필드: `refreshToken`

- `src/main/java/com/example/springapi/web/auth/dto/UserInfoResponse.java`
  - 필드 예시  
    - `username`  
    - `email`  
    - `roles`  
    - 기타 화면 표시에 필요한 최소한의 정보

---

## 3. Vue.js 프론트엔드 연동 준비

### 3.1 CORS 설정

**파일 경로**

- `src/main/java/com/example/springapi/config/WebConfig.java`

**내용**

- Vue 개발 서버 Origin 허용: `http://localhost:5173`
- 허용 헤더: `Authorization`, `Content-Type` 등
- `allowCredentials(true)` 유지
  - 향후 HttpOnly Cookie 기반 인증을 고려한 설정
- 프로덕션 환경용 CORS 설정
  - Spring Profile / 환경 변수 기반 Origin 분리 (dev/stg/prod)

### 3.2 SecurityConfig 에서 CORS/CSRF 처리

**파일 경로**

- `src/main/java/com/example/springapi/config/SecurityConfig.java`

**내용**

- CORS 설정을 `SecurityFilterChain` 에 통합
- `OPTIONS` 메서드 허용 (Preflight 요청 처리)
- `Authorization: Bearer <token>` 헤더를 Security에서 인식하도록 설정
- API 엔드포인트에 대해 CSRF 비활성화
  - Vue.js SPA + REST API 조합을 고려한 설정

### 3.3 API 응답 형식

- 기존 `ApiResponse<T>` 형식을 유지하여 Vue.js와 일관성 확보
- JWT 토큰 응답 시 예시:
  - `{"success": true, "data": { "accessToken": "...", "refreshToken": "...", ... }}`
- 에러 응답도 `ApiResponse` 형식으로 통일

---

## 4. OAuth2/OIDC 리소스 서버 설정 (확장용, 선택적)

### 4.1 SecurityConfig 확장 포인트

**파일 경로**

- `src/main/java/com/example/springapi/config/SecurityConfig.java`

**내용**

- OAuth2 Resource Server 설정 블록 추가 (주석 처리 또는 별도 Profile)
- Keycloak·Auth0 등 OIDC 프로바이더 연동 시 활성화
- 자체 JWT 인증과 OAuth2 인증 경로를 분리 가능하도록 설계

### 4.2 application.properties 설정 (주석)

**파일 경로**

- `src/main/resources/application.properties`

**예시 설정 (주석 처리)**

- `spring.security.oauth2.resourceserver.jwt.issuer-uri=...`
- `spring.security.oauth2.resourceserver.jwt.jwk-set-uri=...`

---

## 5. SFTP 클라이언트 구현

### 5.1 SFTP 클라이언트

**파일 경로**

- `src/main/java/com/example/springapi/common/sftp/SftpClient.java`

**역할**

- SFTP 서버 연결/종료 관리
- 파일 업로드/다운로드 기능
- 디렉토리 생성, 삭제, 목록 조회
- 성능 필요 시 연결 풀 또는 재사용 구조로 확장

### 5.2 SFTP 설정

**파일 경로**

- `src/main/java/com/example/springapi/common/sftp/SftpConfig.java`

**역할**

- SFTP 세션 팩토리 Bean 정의
- 호스트, 포트, 계정, 비밀번호/키 파일 설정
- 연결/읽기 타임아웃 설정 등

### 5.3 SFTP Properties

**파일 경로**

- `src/main/java/com/example/springapi/config/SftpProperties.java`

**역할**

- `@ConfigurationProperties` 활용
- `application.properties` 의 SFTP 관련 설정 값 주입

### 5.4 SFTP 비즈니스 서비스

**파일 경로**

- `src/main/java/com/example/springapi/application/sftp/SftpService.java`

**역할**

- 도메인/업무 관점의 SFTP 로직 구현
- 업로드/다운로드 실패 시 에러 처리 및 재시도 로직
- 필요 시 트랜잭션 관리 (DB 상태와 연계)

### 5.5 SFTP REST API

**파일 경로**

- `src/main/java/com/example/springapi/web/sftp/SftpController.java`

**엔드포인트 (JWT 인증 필요)**

- `POST /api/sftp/upload`  
  - 파일 업로드
- `GET /api/sftp/download`  
  - 파일 다운로드
- `GET /api/sftp/list`  
  - 디렉토리 목록 조회
- `POST /api/sftp/batch` (선택적)  
  - 배치 전용 트리거용 엔드포인트

### 5.6 SFTP DTO

**파일 경로**

- `src/main/java/com/example/springapi/web/sftp/dto/SftpUploadRequest.java`
  - 로컬/업로드 원본 경로
  - SFTP 업로드 대상 경로 등

- `src/main/java/com/example/springapi/web/sftp/dto/SftpDownloadRequest.java`
  - 다운로드 대상 파일 경로

### 5.7 `application.properties` SFTP 설정

**파일 경로**

- `src/main/resources/application.properties`

**예시**

- `sftp.host=...`
- `sftp.port=22`
- `sftp.username=...`
- `sftp.password=...` (또는 키 파일 경로)

---

## 6. SecurityConfig 경로/필터 구성

**파일 경로**

- `src/main/java/com/example/springapi/config/SecurityConfig.java`

### 6.1 JWT 인증 필터 체인

- `JwtAuthenticationFilter` 를 `SecurityFilterChain` 에 등록
- CORS, CSRF, 세션 정책(STATELESS) 설정

### 6.2 경로별 인증 방식 분리

- `/api/auth/login`, `/api/auth/refresh`
  - 인증 불필요 (로그인/토큰 재발급 엔드포인트)
- `/api/auth/me`
  - JWT 인증 필요
- `/api/public/**`
  - 인증 불필요 (헬스체크, 공용 정보 등)
- `/api/sftp/**`
  - JWT 인증 필요
- `/api/**`
  - 기본 JWT 인증 필요

### 6.3 OAuth2 리소스 서버 설정 (주석/프로파일 분리)

- 향후 필요 시 Resource Server 기능 활성화  
  (Keycloak 연동, 외부 토큰 검증 등)

---

## 7. 예외 처리 확장

### 7.1 글로벌 예외 처리기

**파일 경로**

- `src/main/java/com/example/springapi/common/exception/GlobalExceptionHandler.java`

**확장 내용**

- JWT 관련 예외 처리
  - `JwtException` (파싱 실패, 위조 토큰 등)
  - 만료된 토큰 → `EXPIRED_TOKEN`
- Spring Security 관련 예외 처리
  - `AuthenticationException` (인증 실패)
- SFTP 관련 예외 처리
  - `SftpException` (연결 실패, 파일 없음 등)

→ 모두 `ApiResponse` 형식으로 반환  
(에러 코드 + 메시지 + 필요 시 상세 정보)

### 7.2 에러 코드 정의

**파일 경로**

- `src/main/java/com/example/springapi/common/exception/ErrorCode.java`

**JWT 관련**

- `INVALID_TOKEN` : 잘못된 토큰
- `EXPIRED_TOKEN` : 만료된 토큰
- `UNAUTHORIZED` : 인증 필요

**SFTP 관련**

- `SFTP_CONNECTION_FAILED` : 서버 연결 실패
- `SFTP_FILE_NOT_FOUND` : 대상 파일 없음

---

## 8. 설정 관리

### 8.1 JWT Properties

**파일 경로**

- `src/main/java/com/example/springapi/config/JwtProperties.java`

**역할**

- `@ConfigurationProperties("jwt")`
- 설정 항목
  - `secret`
  - `accessTokenExpiration`
  - `refreshTokenExpiration`

### 8.2 application.properties 설정

**파일 경로**

- `src/main/resources/application.properties`

**JWT 설정 예시**

```properties
jwt.secret=${JWT_SECRET}
jwt.access-token-expiration=3600000       # 1시간 (ms)
jwt.refresh-token-expiration=604800000    # 7일 (ms)
