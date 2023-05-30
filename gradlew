@echo off

REM Gradle wrapper script for Windows

SET DIR=%~dp0
cd /D "%DIR%" || exit /b

REM Set the appropriate Gradle version
SET GRADLE_VERSION=7.3.3

REM Check if Gradle wrapper exists; if not, download it
IF NOT EXIST ".\gradlew" (
    echo Downloading Gradle Wrapper %GRADLE_VERSION%...
    rmdir /s /q .gradle
    del /q gradlew*
    powershell -Command "Invoke-WebRequest -Uri ""https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip"" -OutFile ""gradle-%GRADLE_VERSION%-bin.zip"""
    powershell -Command "Expand-Archive -Path ""gradle-%GRADLE_VERSION%-bin.zip"" -DestinationPath ."
    ren "gradle-%GRADLE_VERSION%" gradle
    del /q "gradle-%GRADLE_VERSION%-bin.zip"
    echo Gradle Wrapper %GRADLE_VERSION% downloaded.
)

REM Run Gradle command
gradlew %*
