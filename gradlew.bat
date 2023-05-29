@echo off

REM This is a platform-independent wrapper script for running Gradle tasks.
REM It automatically downloads and installs the correct Gradle version specified in the Gradle wrapper.

REM Gradle version specified in gradle/wrapper/gradle-wrapper.properties
for /f "delims=" %%v in ('findstr /r "distributionUrl=gradle-[0-9]\+\.[0-9]\+\(\.[0-9]\+\)\?-bin.zip" gradle/wrapper/gradle-wrapper.properties') do (
    set "gradle_version=%%v"
)

REM Gradle wrapper URL
set "gradle_url=https://services.gradle.org/distributions/%gradle_version%"

REM Gradle home directory
set "gradle_home=.gradle/wrapper/dists/%gradle_version%-bin"

REM Gradle executable
set "gradle_exec=%gradle_home%/gradle-%gradle_version%/bin/gradle"

REM Check if Gradle distribution is already installed
if not exist "%gradle_home%" (
    echo Downloading Gradle %gradle_version%...
    mkdir "%gradle_home%"
    bitsadmin /transfer "gradle-download" "%gradle_url%" "%gradle_home%/gradle-%gradle_version%.zip"
    powershell -command "& {Add-Type -A 'System.IO.Compression.FileSystem'; [System.IO.Compression.ZipFile]::ExtractToDirectory('%gradle_home%/gradle-%gradle_version%.zip', '%gradle_home%');}"
    del "%gradle_home%/gradle-%gradle_version%.zip"
)

REM Run Gradle
"%gradle_exec%" %*
