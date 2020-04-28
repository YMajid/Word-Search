package com.example.wordsearch

import androidx.lifecycle.ViewModel
import WordSearch

class GameViewModel: ViewModel() {

    private val wordList = listOf(
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
        "Culture",
        "Merchant",
        "Business",
        "Journey",
        "Invest",
        "Software",
        "Powered",
        "Commerce",
        "Retail"
    )

    private val gridSize = 10

    val grid = WordSearch().makeGrid(gridSize, wordList)

}