# Learn the project

Read these files in this order. You do not need to understand everything at once.

## 1. The screen: `activity_main.xml`

The XML contains two `Spinner` drop-downs, a Swap button, a Show route button,
the custom `CampusMapView`, three map-control buttons and a numbered key.

## 2. The data classes: `CampusPlace.kt`

A `data class` is a small container. `MapPoint` stores x/y coordinates,
`MapNode` stores a point on a path, `MapEdge` connects two nodes, and
`CampusPlace` connects a name to its nearest node.

## 3. The local data: `campus_map.json`

The JSON has four main lists:

- `nodes`: points along campus paths;
- `edges`: pairs of node IDs that can be travelled between;
- `places`: names displayed in the selectors;
- `shapes`: simple building, field, court and pool outlines.

`CampusPlaces.load()` converts this JSON into Kotlin objects. Adding a place is
mainly a data change, not a change to the routing algorithm.

## 4. The route: `RouteFinder.kt`

The app uses Dijkstra's shortest-path algorithm:

1. Give the starting node distance 0.
2. Visit the unvisited node with the smallest known distance.
3. Check whether going through it gives its neighbours a shorter distance.
4. Remember the previous node for each improvement.
5. Rebuild the route backwards when the destination is reached.

The graph is tiny, so the straightforward loop is easier to learn than a more
advanced priority-queue implementation.

## 5. The drawing: `CampusMapView.kt`

Android calls `onDraw()`. The view draws background shapes, grey/white path lines,
the blue selected route, then numbered markers. A `Matrix` handles map fitting,
dragging and zooming. Touch events move the map; `ScaleGestureDetector` handles
pinch gestures.

## 6. The activity: `MainActivity.kt`

`onCreate()` loads the map, fills the Spinners and connects click listeners.
`showSelectedRoute()` validates the choice, asks `RouteFinder` for a node list,
then gives that list to the map view.

Try learning by changing one safe thing at a time: a marker colour, a label,
the initial destination, or the map height. Build after each small change.
