package com.example.wordsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(totalTime: Int): ViewModel() {
    private val _playAgain = MutableLiveData<Boolean>()
    val playAgain: LiveData<Boolean>
        get() = _playAgain

    private val _timeTaken = MutableLiveData<Int>()
    val timeTaken: LiveData<Int>
        get() = _timeTaken

    init {
        _timeTaken.value = totalTime
    }

    fun onPlayAgain() {
        _playAgain.value = true
    }

    fun onPlayAgainComplete() {
        _playAgain.value = false
    }
}