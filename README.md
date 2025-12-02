# Spring Boot API Project

Java 11, Spring Boot, MyBatis, Gradle, Spring Security を使用した REST API プロジェクト

## 技術スタック

- Java 11
- Spring Boot 2.7.18
- MyBatis
- Gradle
- Spring Security
- MySQL

## プロジェクト構造

```
spring-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/springapi/
│   │   │       ├── config/          # 設定クラス
│   │   │       ├── controller/      # コントローラー
│   │   │       ├── service/         # サービス層
│   │   │       ├── mapper/          # MyBatis Mapper
│   │   │       ├── dto/             # DTO クラス
│   │   │       ├── entity/          # エンティティクラス
│   │   │       └── security/        # Security 関連クラス
│   │   └── resources/
│   │       ├── mapper/              # MyBatis XML マッパー
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/springapi/
├── build.gradle
└── settings.gradle
```

## セットアップ

1. **Gradle Wrapper の設定** (初回のみ)
   ```powershell
   .\setup-gradle-wrapper.ps1
   ```
   または、Gradle がインストールされている場合:
   ```bash
   gradle wrapper --gradle-version 7.6.3
   ```

2. データベース設定を `application.properties` で設定

3. プロジェクトをビルド: `.\gradlew.bat build` (Windows) または `./gradlew build` (Linux/Mac)

4. アプリケーションを実行: `.\gradlew.bat bootRun` (Windows) または `./gradlew bootRun` (Linux/Mac)

## トラブルシューティング

### Gradle ビルドエラーが発生する場合

- Gradle Wrapper JAR ファイルが存在することを確認: `gradle\wrapper\gradle-wrapper.jar`
- 存在しない場合は `setup-gradle-wrapper.ps1` を実行
- VS Code の Gradle 拡張機能を再読み込み

