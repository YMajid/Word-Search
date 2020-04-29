package com.example.wordsearch

//Different ways words can be placed, and movements necessary to do that
enum class PlacementType {
    leftRight,
    rightLeft,
    upDown,
    downUp,
    topLeftBottomRight,
    topRightBottomLeft,
    bottomLeftTopRight,
    bottomRightTopLeft;

    fun movement(placementType: PlacementType) =
        when (placementType) {
            leftRight -> intArrayOf(0, 1)
            rightLeft -> intArrayOf(-1, 0)
            upDown -> intArrayOf(0, 1)
            downUp -> intArrayOf(0, -1)
            topLeftBottomRight -> intArrayOf(1, 1)
            topRightBottomLeft -> intArrayOf(-1, 1)
            bottomLeftTopRight -> intArrayOf(1, -1)
            bottomRightTopLeft -> intArrayOf(-1, -1)
    }
}
