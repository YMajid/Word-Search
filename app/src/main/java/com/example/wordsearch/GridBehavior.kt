package com.example.wordsearch

import android.view.View
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import timber.log.Timber
import kotlin.math.round

/**
 * Get's coordinates where user touched the screen.
 * TODO works best when touching only the edge of the screen
 */
class GridBehavior(view: View){

    val view = view

    fun getColIndex(screenPosition: Int): Int {
        // 9 is col count in grid
        val floatColIndex: Float = ((screenPosition.toFloat() - view.marginLeft.toFloat()) / view.width.toFloat()).coerceAtMost(0.9f).coerceAtLeast(0f)
        val colIndex: Int = round(floatColIndex*10).toInt()
        Timber.i("floatColIndex: $floatColIndex, colIndex: $colIndex")
        return colIndex
    }

    fun getRowIndex(screenPosition: Int): Int {
        // 9 is row count in grid
        val floatRowIndex: Float = ((screenPosition.toFloat() - view.marginTop.toFloat()) / view.height.toFloat()).coerceAtMost(0.9f).coerceAtLeast(0f)
        val rowIndex: Int = round(floatRowIndex*10).toInt()
        Timber.i("floatRowIndex: $floatRowIndex, rowIndex: $rowIndex")
        return rowIndex
    }

}