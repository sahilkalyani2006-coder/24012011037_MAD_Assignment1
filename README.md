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

## Progress

| Date | Update | Challenge |
| --- | --- | --- |
| 2026-09-06 | Corrected the assignment scope and added From/To selection | Accurate campus geometry was still missing |
| 2026-09-06 | Traced the supplied overview and closeups; added the offline map, 34 places, route engine, map controls and tests | Entrances need a final on-campus check |

