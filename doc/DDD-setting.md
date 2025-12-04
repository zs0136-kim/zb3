# 도메인 서비스 + 포트/어댑터 아키텍처 정리

## 0. 대화 목적

- 기존 ZEBIS2 구조(레이어별 공통 패키지: `cmn/mapper`, `cmn/service`, `web/controller` 등)에서  
- ZEBIS3를 **REST API + Vue.js 연동**에 적합한 구조로 바꾸기 위해
- **“도메인 서비스 + 포트/어댑터 조합 구조”**를 어떻게 설계하고,  
  이 방식이 현재 주류와 얼마나 맞는지 정리한 메모.

---

## 1. 기본 개념

### 1.1 도메인 서비스 (Domain Service)

- 역할: “재고 예약”, “출하 확정”, “회계분개 생성” 등 **비즈니스 규칙** 자체를 구현
- 특징
  - DB, HTTP, 프레임워크(Spring) 의존 X → **순수 Java**
  - 엔티티/ValueObject를 받아서 규칙 수행
  - 트랜잭션, 로깅, 인증 같은 기술적인 부분은 모름

> 한 줄 정의:  
> **“업무 규칙 전용 계산기”** 역할을 하는 계층

---

### 1.2 포트 / 어댑터 (Ports & Adapters)

- 전체 아이디어: **Hexagonal Architecture / Clean Architecture** 계열

#### 1) 포트(Port)

- 코어(도메인/애플리케이션)가 **바깥 세계와 통신하기 위한 인터페이스(계약서)**

1. Inbound Port (입력 포트)
   - 바깥(웹, 배치, 메시지 등)에서 **코어를 호출**하기 위한 인터페이스
   - 예: `AllocateStockUseCase`, `RegisterOrderUseCase`

2. Outbound Port (출력 포트)
   - 코어가 **DB, 외부 시스템**에 접근할 때 사용하는 인터페이스
   - 예: `LoadStockPort`, `SaveStockPort`, `SendInvoicePort`

#### 2) 어댑터(Adapter)

- **포트를 실제 기술에 맞게 구현**하는 계층

1. Inbound Adapter
   - REST 컨트롤러(`@RestController`)
   - 배치 Job, 메시지 Consumer 등

2. Outbound Adapter
   - MyBatis 기반 Repository
   - 외부 REST API 클라이언트
   - 파일 I/O 등

> 한 줄 정의:  
> - **포트** = “이렇게만 호출/구현해줘”라는 인터페이스  
> - **어댑터** = 그 인터페이스를 **Spring/MyBatis/외부 API에 맞게 구현한 클래스**

---

### 1.3 “조합 구조”란?

한 유스케이스를 다음과 같이 **조합해서 구성**하는 패턴:

1. **Inbound Adapter** (예: REST 컨트롤러)  
   → Inbound Port(UseCase 인터페이스) 호출
2. **UseCase 구현체 (Application Service)**  
   → 도메인 서비스 호출  
   → 필요 시 Outbound Port(Repository/외부 API) 호출
3. **도메인 서비스**  
   → 엔티티/ValueObject만으로 비즈니스 규칙 수행
4. **Outbound Adapter**  
   → Outbound Port를 구현 (MyBatis, 외부 API 등)

---

## 2. 도메인 단위 패키지 구조 예시

예: `stock` 도메인

```text
com.example.zebis3
└── stock
    ├── domain
    │   ├── model
    │   │   ├── Stock.java               # 재고 엔티티
    │   │   └── StockItem.java           # 품목별 재고 등
    │   ├── service
    │   │   └── StockAllocationService.java   # 재고 할당 도메인 서비스
    │   └── port
    │       ├── in
    │       │   └── AllocateStockUseCase.java # 유스케이스(입력 포트)
    │       └── out
    │           ├── LoadStockPort.java        # 재고 조회
    │           └── SaveStockPort.java        # 재고 저장
    │
    ├── application
    │   ├── service
    │   │   └── AllocateStockService.java     # UseCase 구현(@Service)
    │   └── dto
    │       ├── AllocateStockCommand.java     # 입력 DTO
    │       └── StockSummaryDto.java          # 출력 DTO
    │
    └── adapter
        ├── in
        │   └── web
        │       └── StockRestController.java  # REST 컨트롤러
        └── out
            ├── persistence
            │   ├── StockMyBatisMapper.java   # MyBatis 매퍼
            │   └── StockMyBatisAdapter.java  # Outbound Port 구현체
            └── external
                └── WarehouseApiAdapter.java  # 외부 창고 시스템 연동 (예)
