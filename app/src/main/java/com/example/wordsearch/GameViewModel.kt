package com.example.wordsearch

import androidx.lifecycle.ViewModel
import WordSearch
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class GameViewModel: ViewModel() {

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 10000L
    }

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    init {
        _score.value = 0
        _eventGameFinished.value = false

        timer = object  : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished)/ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinished.value = true
            }
        }

        timer.start()
    }

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

    fun onGameFinishComplete() {
        _eventGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}