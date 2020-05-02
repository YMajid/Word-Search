package com.example.wordsearch

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ObservableArrayList
import timber.log.Timber

/**
 * View used to display the puzzle
 */
class GridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private val tilePaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
        style = Paint.Style.STROKE
        textSize = 20f * Resources.getSystem().displayMetrics.scaledDensity
    }

    private val textPaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
        textSize = 20f * Resources.getSystem().displayMetrics.scaledDensity
    }

    var data: List<List<Char>> = listOf(
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'),
        listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j')
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        data.forEachIndexed { row, chars ->
            chars.forEachIndexed { column, char ->
//                canvas.drawRect(column * tileWidth(), row * tileHeight(), (column+1) * tileWidth(), (row+1) * tileHeight(), tilePaint)
                canvas.drawText(char.toString(), horizontalCenterOfTile(column), verticalCenterOfTile(row), textPaint)
            }
        }
    }

    var startPosition: IntArray = intArrayOf(-1, -1)
    var finishPosition: IntArray = intArrayOf(-1, -1)
    var startX: Int = -1
    var startY: Int = -1
    var finishX: Int = -1
    var finishY: Int = -1

//    val touchPoint = mutableListOf<Array<IntArray>>()
    val touchPoint = ObservableArrayList<Array<IntArray>>()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = GridBehavior(this).getColIndex(event.x.toInt())
                startY = GridBehavior(this).getRowIndex(event.y.toInt())
                startPosition = intArrayOf(startX, startY)
            }
            MotionEvent.ACTION_UP -> {
                finishX = GridBehavior(this).getColIndex(event.x.toInt())
                finishY = GridBehavior(this).getRowIndex(event.y.toInt())
                finishPosition = intArrayOf(finishX, finishY)
                touchPoint.add(arrayOf(startPosition, finishPosition))
                Timber.i("startPosition: (${startPosition[0]}, ${startPosition[1]}), finishPosition: (${finishPosition[0]}, ${finishPosition[1]})")
            }
        }
        return true
    }

    private fun horizontalCenterOfTile(column: Int) = (column * tileWidth()) + tileWidth() / 2
    private fun verticalCenterOfTile(row: Int) = (row * tileHeight()) + tileHeight() / 2

    private fun tileWidth() = width / data.size.toFloat()
    private fun tileHeight() = height / data.size.toFloat()
}