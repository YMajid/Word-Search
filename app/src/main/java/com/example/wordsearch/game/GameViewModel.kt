package com.example.wordsearch.game

import WordSearch
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.wordsearch.wordPlacement.Word

class GameViewModel : ViewModel() {

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    var score = 0

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

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
        "Build",
        "Business",
        "Journey",
        "Market",
        "Explore",
        "Invest",
        "Software",
        "Powered",
        "Commerce",
        "Retail"
    )

    private val gridSize = 10

    lateinit var grid: List<List<Char>>
    lateinit var usedWordsList: List<Word>
    lateinit var usedWordString: String

    init {
        _eventGameFinished.value = false
        createPuzzle(gridSize, wordList)

        timer = object : CountDownTimer(
            COUNTDOWN_TIME,
            ONE_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished) / ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinished.value = true
            }
        }

        timer.start()
    }

    /**
     * Returns the puzzle, a list and string of the words used.
     */
    private fun createPuzzle(size: Int, words: List<String>) {
        val wordSearch = WordSearch()
        grid = wordSearch.createGrid(size, words)
        usedWordsList = wordSearch.usedWordsList
        usedWordString = "Look for: "
        wordSearch.usedWordsList.forEachIndexed { index, word ->
            usedWordString += if (index == 0) {
                " ${word.word}"
            } else {
                ", ${word.word}"
            }
        }
    }

    fun onGameFinishComplete() {
        _eventGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}