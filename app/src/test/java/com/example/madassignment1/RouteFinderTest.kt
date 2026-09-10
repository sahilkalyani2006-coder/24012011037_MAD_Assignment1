package com.example.madassignment1

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RouteFinderTest {
    private val nodes = listOf(
        MapNode("a", MapPoint(0f, 0f), ""),
        MapNode("b", MapPoint(2f, 0f), ""),
        MapNode("c", MapPoint(4f, 0f), ""),
        MapNode("long", MapPoint(2f, 5f), ""),
        MapNode("alone", MapPoint(9f, 9f), "")
    ).associateBy { it.id }
    private val map = CampusMap(
        10f, 10f, nodes,
        listOf(MapEdge("a", "b"), MapEdge("b", "c"), MapEdge("a", "long"), MapEdge("long", "c")),
        emptyList(), emptyList()
    )

    @Test fun shortestConnectedPathIsChosen() {
        assertEquals(listOf("a", "b", "c"), RouteFinder.find(map, "a", "c"))
    }

    @Test fun reversePathWorks() {
        assertEquals(listOf("c", "b", "a"), RouteFinder.find(map, "c", "a"))
    }

    @Test fun sameStartAndDestinationReturnsOneNode() {
        assertEquals(listOf("b"), RouteFinder.find(map, "b", "b"))
    }

    @Test fun disconnectedDestinationReturnsEmptyList() {
        assertTrue(RouteFinder.find(map, "a", "alone").isEmpty())
    }
}
