package com.example.wordsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.IllegalArgumentException

class ScoreViewModelFactory(private val totalTime: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(totalTime) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}