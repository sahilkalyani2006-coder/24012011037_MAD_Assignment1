#!/usr/bin/env bash
# Small launcher: fetch the official, checksum-verified wrapper JAR on first use.
set -euo pipefail
project_dir="$(cd "$(dirname "$0")" && pwd)"
wrapper_jar="$project_dir/gradle/wrapper/gradle-wrapper.jar"
if [[ ! -f "$wrapper_jar" ]]; then
    bash "$project_dir/scripts/setup-wrapper.sh"
fi
java_command="java"
if [[ -n "${JAVA_HOME:-}" ]]; then
    java_command="$JAVA_HOME/bin/java"
fi
exec "$java_command" -Xmx64m -Dorg.gradle.appname=gradlew \
    -classpath "$wrapper_jar" org.gradle.wrapper.GradleWrapperMain "$@"
