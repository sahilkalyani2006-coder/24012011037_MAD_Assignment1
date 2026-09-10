# Testing checklist

## Checks already performed

- All 34 place IDs and 83 node IDs are unique.
- Every edge and place references an existing node.
- Every pair of selectable places has a route.
- Routes work in reverse and same-node routes work.
- UVPCE New → Nescafe and MBA → Nescafe are different routes.
- Two previews were rendered directly from the Android JSON data.

Run the repeatable data check with `python3 scripts/validate_map.py`.

## Android Studio checks before submission

1. Sync Gradle and run `./gradlew testDebugUnitTest lintDebug assembleDebug`.
2. Install the debug APK on an API 26+ emulator or phone.
3. Check both requested Nescafe examples.
4. Check same start/destination shows a message.
5. Swap the selections and confirm the route reverses.
6. Drag, pinch, zoom in/out and Fit the map.
7. Rotate the device and confirm the route returns.
8. Check the long place names in both selectors.
9. Compare the main gate, academic, sports and southern areas with the screenshots.
10. Walk-check the UVPCE New and MBA entrances before treating routes as final.

The local creation environment did not contain Android SDK or Kotlin compiler, so
the Android build and device steps remain honest manual checks.
