# 24012011037_MAD_Assignment1

## Campus Companion — Ganpat University Route Finder

This is a small Kotlin/XML Android application for finding a route between two
places inside Ganpat University, Kherva. The map is built into the app and works
offline. It does not use Google Maps, GPS, an API key, a login, or a database.

## Objectives

- Learn a basic Android Activity, XML layout, Spinner, Button and custom View.
- Keep campus places in one readable local JSON file.
- Calculate a shortest path through connected campus walkways.
- Draw the chosen route on a simple campus map.
- Keep the project small enough to understand and explain.

## Features

- 34 selectable places traced from the supplied overview and close-up maps.
- Independent starting-point and destination selectors.
- Swap button and same-place validation.
- Offline Dijkstra route calculation.
- Blue route drawn inside the app; selected endpoints appear red.
- Drag, pinch-to-zoom, `+`, `−`, and Fit controls.
- Numbered map key so labels remain readable on a phone.
- Selections and displayed route survive screen rotation.

## Example routes

| Starting point | Destination | What the app shows |
| --- | --- | --- |
| UVPCE New Building | Nescafe | Path from the new engineering block through the academic road to the cricket-ground side |
| MBA Building (VMPIM New) | Nescafe | A different path from the MBA/auditorium side to the same cafe |

Preview images generated from the same JSON data are in
[`docs/preview_uvpce_to_nescafe.png`](docs/preview_uvpce_to_nescafe.png) and
[`docs/preview_mba_to_nescafe.png`](docs/preview_mba_to_nescafe.png).

## How it works

1. `CampusPlaces.load()` reads `app/src/main/assets/campus_map.json`.
2. Each place points to a nearby path node.
3. `RouteFinder.find()` chooses the shortest connected path.
4. `CampusMapView` draws fields, buildings, roads, markers and the blue route.
5. `MainActivity` connects the selectors and buttons to those classes.

Read [`docs/LEARN.md`](docs/LEARN.md) for a beginner-friendly walkthrough.

## Open in Android Studio

1. Extract the ZIP.
2. Open the folder `24012011037_MAD_Assignment1` in Android Studio.
3. Select JDK 17 and install Android SDK Platform 35 / Build-Tools 35.0.0.
4. Let Gradle sync, then run on an Android 8.0 (API 26) or newer device.

The binary Gradle wrapper JAR could not be downloaded in the creation environment.
If Android Studio reports that it is missing, run `bash scripts/setup-wrapper.sh`
on macOS/Linux or `powershell -NoProfile -File scripts/setup-wrapper.ps1` on
Windows. The script downloads the official JAR and verifies its checksum.

Build versions: Kotlin 2.1.20, Android Gradle Plugin 8.9.2, Gradle 8.11.1,
Java 17, compile/target SDK 35, minimum SDK 26.

## Map accuracy and challenges

The schematic was manually traced from the seven supplied satellite screenshots.
The screenshots overlap at different zoom levels, so they first had to be aligned.
The road shapes and relative building positions follow those images, but individual
entrances and small footpaths are approximate. This is an educational campus guide,
not safety-critical live navigation, and it does not report invented distances.

The screenshot labels the large southeast block as Maritime Studies and fotonVR,
not “UVPCE New Building.” This project treats that block as the UVPCE New / new
engineering block based on the provided layout and university references. Its
mapping is one line in the JSON and is easy to correct after an on-campus check.

## Validation

`python3 scripts/validate_map.py` checked all 34 × 34 place combinations, missing
references, duplicate IDs, reverse routes and the two requested examples. Four
JUnit tests cover the route algorithm. XML/resource references and the project
structure are checked by `scripts/check_project.py`.

The Android SDK and Kotlin compiler are unavailable in the creation environment,
so an APK build, Android lint and emulator/device run have not been performed here.
Run the checklist in [`docs/TESTING.md`](docs/TESTING.md) before submission.

## Progress

| Date | Update | Challenge |
| --- | --- | --- |
| 2026-09-06 | Corrected the assignment scope and added From/To selection | Accurate campus geometry was still missing |
| 2026-09-06 | Traced the supplied overview and closeups; added the offline map, 34 places, route engine, map controls and tests | Entrances need a final on-campus check |

## GitHub

Use repository name **24012011037_MAD_Assignment1**. The included GitHub Actions
workflow builds, tests and lints after pushes. Setup steps are in
[`docs/GITHUB.md`](docs/GITHUB.md). A conservative daily-update automation prompt
is prepared in [`docs/DAILY_AUTOMATION.md`](docs/DAILY_AUTOMATION.md); it commits
only real progress and does not create fake empty commits.

The GitHub account is not connected in this session, so the repository and scheduled
push automation have not been created yet.
