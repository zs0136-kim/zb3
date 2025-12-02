# Gradle Wrapper JAR ファイルをダウンロードするスクリプト
$wrapperJarUrl = "https://raw.githubusercontent.com/gradle/gradle/v7.6.3/gradle/wrapper/gradle-wrapper.jar"
$wrapperJarPath = "gradle\wrapper\gradle-wrapper.jar"

# ディレクトリが存在しない場合は作成
if (-not (Test-Path "gradle\wrapper")) {
    New-Item -ItemType Directory -Path "gradle\wrapper" -Force | Out-Null
}

# JAR ファイルをダウンロード
Write-Host "Gradle Wrapper JAR をダウンロード中..." -ForegroundColor Yellow

$ErrorActionPreference = "Stop"
try {
    Invoke-WebRequest -Uri $wrapperJarUrl -OutFile $wrapperJarPath -UseBasicParsing
    Write-Host "Gradle Wrapper JAR のダウンロードが完了しました。" -ForegroundColor Green
}
catch {
    Write-Host "エラー: Gradle Wrapper JAR のダウンロードに失敗しました。" -ForegroundColor Red
    Write-Host "手動でダウンロードしてください: $wrapperJarUrl" -ForegroundColor Yellow
    throw
}
