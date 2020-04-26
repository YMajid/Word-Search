package com.example.wordsearch

class WordSearch {
    val letters = "ABCDEFGHIJKLMOPQRSTUVWSYZ"

    val wordList = listOf(
        "Kotlin",
        "Swift",
        "Java",
        "ObjectiveC",
        "Variable",
        "Mobile",
        "Shopify",
        "Fall",
        "Challenge",
        "Android",
        "Engineer",
        "Intern",
        "Store",
        "Merchant"
    )

    val gridSize = 10

    fun makeGrid(size: Int) {
        val row = mutableListOf<Char>()
        for (i in 1..size) {
            row += ' '
        }
        val grid = mutableListOf<MutableList<Char>>()
        for (i in 1..size) {
            grid += row
        }

        fillSlots(grid)
        printGrid(grid)
    }

    //TODO all of the rows this returns are the same
    fun fillSlots(grid: MutableList<MutableList<Char>>) {
        for (row in grid) {
            val rowIterator = row.listIterator()
            rowIterator.forEach { slot ->
                if (slot == ' ') {
                    rowIterator.set(letters.random())
                }
            }
        }
    }

    fun printGrid(grid: MutableList<MutableList<Char>>) {
        for (row in grid) {
            for (slot in row) {
                print(slot)
            }
            println("")
        }
    }

    fun findSlot(x: Int, y:Int, word: String, movement: IntArray, grid: MutableList<MutableList<Char>>): MutableList<Char>? {
        var letterList = mutableListOf<Char>()
        var xPosition = x
        var yPosition = y

        word.forEach { letter ->
            var slot = grid[xPosition][yPosition]

            if (slot == ' ' || slot == letter) {
                letterList.add(slot)
                xPosition += movement[0]
                yPosition += movement[1]
            } else {
                return null
            }
        }

        return letterList
    }

    fun tryPlaceWord(word: String, movement: IntArray, size: Int, grid: MutableList<MutableList<Char>>): Boolean {
        val xLength = movement[0]*(word.length-1)
        val yLength = movement[1]*(word.length-1)

        val rows = (0..size).random()
        val columns = (0..size).random()

        for (row in 0..rows) {
            for (column in 0..columns) {
                val xFinal = xLength+row
                val yFinal = yLength+column

                if ((xFinal in 0 until size) && (yFinal in 0 until size)) {
                    val letterList = findSlot(column, row, word, movement, grid)
                    if (letterList != null) {
                        word.forEachIndexed {index, letter ->
                            letterList[index] = letter
                        }
                        return true
                    }
                }
            }
        }
        return false
    }

    val placementType = arrayListOf<PlacementType>(PlacementType.leftRight, PlacementType.rightLeft, PlacementType.upDown, PlacementType.downUp,
        PlacementType.topLeftBottomRight, PlacementType.topRightBottomLeft, PlacementType.bottomLeftTopRight, PlacementType.bottomRightTopLeft).shuffled()

    fun placeWord(word: String, size: Int, grid: MutableList<MutableList<Char>>): Boolean {
        val formattedWord = word.toUpperCase()

        for (type in placementType) {
            //TODO Refactor movement function
            if (tryPlaceWord(formattedWord, type.movement(type), size, grid)) {
                return true
            }
        }
        return false
    }
}