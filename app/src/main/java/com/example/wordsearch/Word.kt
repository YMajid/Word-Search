package com.example.wordsearch

class Word(word: String, startCoords: IntArray, endCoords: IntArray) {
    val word = word
    var found = false
    val start = startCoords
    val end = endCoords
}