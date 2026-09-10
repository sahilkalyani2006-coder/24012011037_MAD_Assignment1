package com.example.madassignment1

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object CampusPlaces {
    fun load(context: Context): CampusMap {
        val text = context.assets.open("campus_map.json").bufferedReader().use { it.readText() }
        val root = JSONObject(text)
        val nodes = mutableMapOf<String, MapNode>()
        root.getJSONArray("nodes").forEachObject { item ->
            val node = MapNode(
                item.getString("id"),
                MapPoint(item.getDouble("x").toFloat(), item.getDouble("y").toFloat()),
                item.optString("label")
            )
            nodes[node.id] = node
        }
        val edges = mutableListOf<MapEdge>()
        root.getJSONArray("edges").forEachArray { item ->
            edges += MapEdge(item.getString(0), item.getString(1))
        }
        val places = mutableListOf<CampusPlace>()
        root.getJSONArray("places").forEachObject { item ->
            places += CampusPlace(
                item.getString("id"), item.getString("name"),
                item.getString("node"), item.getString("shortName")
            )
        }
        val shapes = mutableListOf<MapShape>()
        root.getJSONArray("shapes").forEachObject { item ->
            val points = mutableListOf<MapPoint>()
            item.getJSONArray("points").forEachArray { point ->
                points += MapPoint(point.getDouble(0).toFloat(), point.getDouble(1).toFloat())
            }
            shapes += MapShape(item.getString("kind"), points)
        }
        return CampusMap(
            root.getDouble("width").toFloat(), root.getDouble("height").toFloat(),
            nodes, edges, places, shapes
        )
    }
}

private inline fun JSONArray.forEachObject(action: (JSONObject) -> Unit) {
    for (index in 0 until length()) action(getJSONObject(index))
}

private inline fun JSONArray.forEachArray(action: (JSONArray) -> Unit) {
    for (index in 0 until length()) action(getJSONArray(index))
}
