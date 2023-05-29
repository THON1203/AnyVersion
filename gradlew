#!/bin/bash

# This is a platform-independent wrapper script for running Gradle tasks.
# It automatically downloads and installs the correct Gradle version specified in the Gradle wrapper.

# Gradle version specified in gradle/wrapper/gradle-wrapper.properties
gradle_version=$(grep -oP '(?<=gradle-)[0-9]+\.[0-9]+(\.[0-9]+)?(?=-bin\.zip)' gradle/wrapper/gradle-wrapper.properties)

# Gradle wrapper URL
gradle_url="https://services.gradle.org/distributions/gradle-${gradle_version}-bin.zip"

# Gradle home directory
gradle_home=".gradle/wrapper/dists/gradle-${gradle_version}-bin"

# Gradle executable
gradle_exec="${gradle_home}/gradle-${gradle_version}/bin/gradle"

# Check if Gradle distribution is already installed
if [ ! -d "${gradle_home}" ]; then
    echo "Downloading Gradle ${gradle_version}..."
    mkdir -p "${gradle_home}"
    curl -L "${gradle_url}" -o "${gradle_home}/gradle-${gradle_version}.zip"
    unzip -q "${gradle_home}/gradle-${gradle_version}.zip" -d "${gradle_home}"
    rm "${gradle_home}/gradle-${gradle_version}.zip"
fi

# Run Gradle
"${gradle_exec}" "$@"
