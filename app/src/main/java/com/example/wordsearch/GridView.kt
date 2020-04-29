package com.example.wordsearch

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProviders

class GridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
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
                canvas.drawRect(column * tileWidth(), row * tileHeight(), (column+1) * tileWidth(), (row+1) * tileHeight(), tilePaint)
                canvas.drawText(char.toString(), horizontalCenterOfTile(column), verticalCenterOfTile(row), textPaint)
            }
        }
    }

    private fun horizontalCenterOfTile(column: Int) = (column * tileWidth()) + tileWidth() / 2
    private fun verticalCenterOfTile(row: Int) = (row * tileHeight()) + tileHeight() / 2

    private fun tileWidth() = width / data.size.toFloat()
    private fun tileHeight() = height / data.size.toFloat()
}