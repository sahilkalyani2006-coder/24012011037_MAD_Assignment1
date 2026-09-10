package com.example.madassignment1

import kotlin.math.hypot

object RouteFinder {
    fun find(map: CampusMap, start: String, destination: String): List<String> {
        if (start == destination) return listOf(start)
        val neighbours = map.nodes.keys.associateWith { mutableListOf<Pair<String, Double>>() }
        map.edges.forEach { edge ->
            val first = map.nodes.getValue(edge.first).point
            val second = map.nodes.getValue(edge.second).point
            val length = hypot((first.x - second.x).toDouble(), (first.y - second.y).toDouble())
            neighbours.getValue(edge.first) += edge.second to length
            neighbours.getValue(edge.second) += edge.first to length
        }
        val distances = mutableMapOf(start to 0.0)
        val previous = mutableMapOf<String, String>()
        val unvisited = map.nodes.keys.toMutableSet()
        while (unvisited.isNotEmpty()) {
            val current = unvisited.minByOrNull { distances[it] ?: Double.MAX_VALUE } ?: break
            if (distances[current] == null || current == destination) break
            unvisited.remove(current)
            neighbours.getValue(current).forEach { (next, length) ->
                if (next in unvisited) {
                    val newDistance = distances.getValue(current) + length
                    if (newDistance < (distances[next] ?: Double.MAX_VALUE)) {
                        distances[next] = newDistance
                        previous[next] = current
                    }
                }
            }
        }
        if (destination !in distances) return emptyList()
        val route = mutableListOf(destination)
        while (route.last() != start) route += previous.getValue(route.last())
        return route.reversed()
    }
}
