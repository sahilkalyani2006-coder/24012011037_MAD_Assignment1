# Build setup only; this script is not part of the Android application.
$ErrorActionPreference = 'Stop'
$projectDirectory = Split-Path $PSScriptRoot -Parent
$wrapperDirectory = Join-Path $projectDirectory 'gradle/wrapper'
$temporaryJar = Join-Path $wrapperDirectory ('wrapper-' + [guid]::NewGuid() + '.tmp')
$expectedHash = '2db75c40782f5e8ba1fc278a5574bab070adccb2d21ca5a6e5ed840888448046'
try {
    Invoke-WebRequest -UseBasicParsing -Uri 'https://raw.githubusercontent.com/gradle/gradle/v8.11.1/gradle/wrapper/gradle-wrapper.jar' -OutFile $temporaryJar
    $actualHash = (Get-FileHash -Algorithm SHA256 -Path $temporaryJar).Hash.ToLowerInvariant()
    if ($actualHash -ne $expectedHash) {
        throw 'Wrapper checksum mismatch. The download was not installed.'
    }
    Move-Item -Force $temporaryJar (Join-Path $wrapperDirectory 'gradle-wrapper.jar')
    Write-Host 'Gradle wrapper JAR is ready.'
} finally {
    if (Test-Path $temporaryJar) { Remove-Item $temporaryJar }
}
