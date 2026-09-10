package com.example.madassignment1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : BaseActivity() {
    private lateinit var startSpinner: Spinner
    private lateinit var destinationSpinner: Spinner
    private lateinit var mapView: CampusMapView
    private lateinit var statusText: TextView
    private lateinit var campusMap: CampusMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        campusMap = CampusPlaces.load(this)
        startSpinner = findViewById(R.id.startSpinner)
        destinationSpinner = findViewById(R.id.destinationSpinner)
        mapView = findViewById(R.id.mapView)
        statusText = findViewById(R.id.statusText)
        mapView.setMap(campusMap)

        val adapter = ArrayAdapter(this, R.layout.item_place, campusMap.places.map { it.name })
        adapter.setDropDownViewResource(R.layout.item_place)
        startSpinner.adapter = adapter
        destinationSpinner.adapter = adapter
        startSpinner.setSelection(savedInstanceState?.getInt("start") ?: 0)
        destinationSpinner.setSelection(savedInstanceState?.getInt("destination") ?: 2)

        val clearListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mapView.clearRoute()
                statusText.setText(R.string.choose_and_show)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
        startSpinner.onItemSelectedListener = clearListener
        destinationSpinner.onItemSelectedListener = clearListener

        findViewById<Button>(R.id.swapButton).setOnClickListener {
            val oldStart = startSpinner.selectedItemPosition
            startSpinner.setSelection(destinationSpinner.selectedItemPosition)
            destinationSpinner.setSelection(oldStart)
        }
        findViewById<Button>(R.id.routeButton).setOnClickListener { showSelectedRoute() }
        findViewById<Button>(R.id.zoomInButton).setOnClickListener { mapView.zoomBy(1.25f) }
        findViewById<Button>(R.id.zoomOutButton).setOnClickListener { mapView.zoomBy(0.8f) }
        findViewById<Button>(R.id.fitButton).setOnClickListener { mapView.fitCampus() }

        findViewById<TextView>(R.id.legendText).text = campusMap.places.mapIndexed { index, place ->
            "${index + 1}. ${place.shortName}"
        }.joinToString("   •   ")

        if (savedInstanceState?.getBoolean("routeShown") == true) {
            mapView.post { showSelectedRoute() }
        }
    }

    private fun showSelectedRoute() {
        val start = campusMap.places[startSpinner.selectedItemPosition]
        val destination = campusMap.places[destinationSpinner.selectedItemPosition]
        if (start == destination) {
            mapView.clearRoute()
            Toast.makeText(this, R.string.same_place, Toast.LENGTH_SHORT).show()
            return
        }
        val route = RouteFinder.find(campusMap, start.nodeId, destination.nodeId)
        if (route.isEmpty()) {
            statusText.setText(R.string.no_route)
            mapView.clearRoute()
        } else {
            mapView.showRoute(start.nodeId, destination.nodeId, route)
            statusText.text = getString(R.string.route_result, start.name, destination.name)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("start", startSpinner.selectedItemPosition)
        outState.putInt("destination", destinationSpinner.selectedItemPosition)
        outState.putBoolean("routeShown", statusText.text != getString(R.string.choose_and_show))
        super.onSaveInstanceState(outState)
    }
}
