package com.example.madassignment1

data class MapPoint(val x: Float, val y: Float)

data class MapNode(val id: String, val point: MapPoint, val label: String)

data class MapEdge(val first: String, val second: String)

data class CampusPlace(
    val id: String,
    val name: String,
    val nodeId: String,
    val shortName: String
)

data class MapShape(val kind: String, val points: List<MapPoint>)

data class CampusMap(
    val width: Float,
    val height: Float,
    val nodes: Map<String, MapNode>,
    val edges: List<MapEdge>,
    val places: List<CampusPlace>,
    val shapes: List<MapShape>
)
