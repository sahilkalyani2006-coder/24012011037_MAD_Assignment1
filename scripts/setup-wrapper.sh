#!/usr/bin/env bash
# Build setup only; this script is not part of the Android application.
set -euo pipefail
project_dir="$(cd "$(dirname "$0")/.." && pwd)"
wrapper_dir="$project_dir/gradle/wrapper"
expected_hash="2db75c40782f5e8ba1fc278a5574bab070adccb2d21ca5a6e5ed840888448046"
temporary_jar="$(mktemp "$wrapper_dir/wrapper.XXXXXX")"
trap 'rm -f "$temporary_jar"' EXIT
curl --fail --location --retry 2 --connect-timeout 15 --max-time 120 \
    "https://raw.githubusercontent.com/gradle/gradle/v8.11.1/gradle/wrapper/gradle-wrapper.jar" \
    --output "$temporary_jar"
if command -v sha256sum >/dev/null 2>&1; then
    actual_hash="$(sha256sum "$temporary_jar" | cut -d ' ' -f 1)"
else
    actual_hash="$(shasum -a 256 "$temporary_jar" | cut -d ' ' -f 1)"
fi
if [[ "$actual_hash" != "$expected_hash" ]]; then
    echo "Wrapper checksum mismatch. The download was not installed." >&2
    exit 1
fi
mv "$temporary_jar" "$wrapper_dir/gradle-wrapper.jar"
echo "Gradle wrapper JAR is ready."
