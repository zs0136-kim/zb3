./gradlew clean build
./gradlew bootRun

./gradlew.bat bootRun --args="--spring.profiles.active=local"


./gradlew tasks : 利用可能なタスク一覧を確認
./gradlew bootRun : アプリケーション実行（現在はlocalプロファイル自動適用）
./gradlew test : 全テスト実行
./gradlew clean : ビルド成果物削除
./gradlew build : テスト込みで全ビルド（JAR生成）
./gradlew bootJar : Spring Boot 実行JARのみ生成
./gradlew dependencies : 依存関係ツリー確認
./gradlew --stop : デーモン停止（環境が壊れた場合などに便利）

現在のディレクトリ表示: pwd（PowerShellでも使用可能）
一つ上の階層へ移動: cd ..
絶対パスで移動: cd C:\workspace_api\spring_api
ホームディレクトリへ移動: cd ~
スペース含むパスの場合: cd "C:\path with space\project"
前のディレクトリに戻る: cd -（PowerShellではサポートされていないかも; その場合は直接パスを入力）