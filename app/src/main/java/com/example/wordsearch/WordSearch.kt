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
        "Android"
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
}