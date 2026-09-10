@echo off
setlocal
set "PROJECT_DIR=%~dp0"
if not exist "%PROJECT_DIR%gradle\wrapper\gradle-wrapper.jar" (
    powershell -NoProfile -File "%PROJECT_DIR%scripts\setup-wrapper.ps1"
    if errorlevel 1 exit /b 1
)
set "JAVA_COMMAND=java.exe"
if defined JAVA_HOME set "JAVA_COMMAND=%JAVA_HOME%\bin\java.exe"
"%JAVA_COMMAND%" -Xmx64m -Dorg.gradle.appname=gradlew -classpath "%PROJECT_DIR%gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
exit /b %ERRORLEVEL%
