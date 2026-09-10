package com.example.madassignment1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import kotlin.math.max
import kotlin.math.min

class CampusMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mapMatrix = Matrix()
    private val scaler = ScaleGestureDetector(context, ScaleListener())
    private var campusMap: CampusMap? = null
    private var route: List<String> = emptyList()
    private var startNode: String? = null
    private var destinationNode: String? = null
    private var lastX = 0f
    private var lastY = 0f
    private var hasFitted = false

    fun setMap(map: CampusMap) {
        campusMap = map
        hasFitted = false
        invalidate()
    }

    fun showRoute(start: String, destination: String, nodeIds: List<String>) {
        startNode = start
        destinationNode = destination
        route = nodeIds
        zoomToRoute()
        invalidate()
    }

    fun clearRoute() {
        startNode = null
        destinationNode = null
        route = emptyList()
        invalidate()
    }

    fun fitCampus() {
        val map = campusMap ?: return
        if (width == 0 || height == 0) return
        mapMatrix.reset()
        val scale = min(width / map.width, height / map.height) * 0.94f
        mapMatrix.postScale(scale, scale)
        mapMatrix.postTranslate((width - map.width * scale) / 2f, (height - map.height * scale) / 2f)
        hasFitted = true
        invalidate()
    }

    fun zoomBy(factor: Float) {
        mapMatrix.postScale(factor, factor, width / 2f, height / 2f)
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (!hasFitted) fitCampus()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val map = campusMap ?: return
        canvas.drawColor(Color.rgb(239, 244, 234))
        canvas.save()
        canvas.concat(mapMatrix)
        drawShapes(canvas, map)
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = Color.rgb(184, 190, 187)
        paint.strokeWidth = 15f
        map.edges.forEach { drawEdge(canvas, map, it.first, it.second) }
        paint.color = Color.WHITE
        paint.strokeWidth = 5f
        map.edges.forEach { drawEdge(canvas, map, it.first, it.second) }
        if (route.isNotEmpty()) {
            paint.color = Color.rgb(33, 116, 208)
            paint.strokeWidth = 12f
            route.zipWithNext().forEach { drawEdge(canvas, map, it.first, it.second) }
        }
        drawPlaces(canvas, map)
        canvas.restore()
    }

    private fun drawShapes(canvas: Canvas, map: CampusMap) {
        map.shapes.forEach { shape ->
            if (shape.points.size < 3) return@forEach
            val path = Path().apply {
                moveTo(shape.points.first().x, shape.points.first().y)
                shape.points.drop(1).forEach { lineTo(it.x, it.y) }
                close()
            }
            paint.style = Paint.Style.FILL
            paint.color = when (shape.kind) {
                "field" -> Color.rgb(193, 218, 167)
                "court" -> Color.rgb(184, 205, 192)
                "pool" -> Color.rgb(106, 196, 220)
                else -> Color.rgb(218, 205, 180)
            }
            canvas.drawPath(path, paint)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            paint.color = Color.rgb(130, 139, 125)
            canvas.drawPath(path, paint)
        }
    }

    private fun drawPlaces(canvas: Canvas, map: CampusMap) {
        val selected = setOfNotNull(startNode, destinationNode)
        map.places.forEachIndexed { index, place ->
            val point = map.nodes.getValue(place.nodeId).point
            paint.style = Paint.Style.FILL
            paint.color = if (place.nodeId in selected) Color.rgb(224, 75, 67) else Color.rgb(48, 91, 123)
            canvas.drawCircle(point.x, point.y, if (place.nodeId in selected) 17f else 13f, paint)
            paint.color = Color.WHITE
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = 14f
            paint.isFakeBoldText = true
            canvas.drawText((index + 1).toString(), point.x, point.y + 5f, paint)
        }
        paint.isFakeBoldText = false
    }

    private fun drawEdge(canvas: Canvas, map: CampusMap, firstId: String, secondId: String) {
        val first = map.nodes.getValue(firstId).point
        val second = map.nodes.getValue(secondId).point
        canvas.drawLine(first.x, first.y, second.x, second.y, paint)
    }

    private fun zoomToRoute() {
        val map = campusMap ?: return
        if (route.isEmpty() || width == 0 || height == 0) return
        val points = route.map { map.nodes.getValue(it).point }
        val bounds = RectF(
            points.minOf { it.x }, points.minOf { it.y },
            points.maxOf { it.x }, points.maxOf { it.y }
        )
        bounds.inset(-90f, -90f)
        mapMatrix.setRectToRect(
            bounds, RectF(16f, 16f, width - 16f, height - 16f), Matrix.ScaleToFit.CENTER
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaler.onTouchEvent(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> { lastX = event.x; lastY = event.y }
            MotionEvent.ACTION_MOVE -> if (!scaler.isInProgress) {
                mapMatrix.postTranslate(event.x - lastX, event.y - lastY)
                lastX = event.x
                lastY = event.y
                invalidate()
            }
            MotionEvent.ACTION_UP -> performClick()
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val factor = max(0.8f, min(detector.scaleFactor, 1.25f))
            mapMatrix.postScale(factor, factor, detector.focusX, detector.focusY)
            invalidate()
            return true
        }
    }
}
