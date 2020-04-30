package com.example.wordsearch

import android.view.View

class GridBehavior(view: View){

    val view = view

    fun getColIndex(screenPosition: Int): Int {
        // 9 is col count in grid
        return ((screenPosition - view.paddingLeft) / view.width).coerceAtMost(9).coerceAtLeast(0)
    }

    fun getRowIndex(screenPosition: Int): Int {
        // 9 is row count in grid
        return ((screenPosition - view.paddingTop) / view.height).coerceAtMost(9).coerceAtLeast(0)
    }

}