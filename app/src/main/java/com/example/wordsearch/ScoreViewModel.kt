package com.example.wordsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {
    private val _playAgain = MutableLiveData<Boolean>()
    val playAgain: LiveData<Boolean>
        get() = _playAgain

    private val _finalScore = MutableLiveData<Int>()
    val finalScore: LiveData<Int>
        get() = _finalScore

    init {
        _finalScore.value = finalScore
    }

    fun onPlayAgain() {
        _playAgain.value = true
    }

    fun onPlayAgainComplete() {
        _playAgain.value = false
    }
}