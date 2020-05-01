import com.example.wordsearch.PlacementType
import com.example.wordsearch.Word
import timber.log.Timber

class WordSearch {

    private val letters = "ABCDEFGHIJKLMOPQRSTUVWSYZ"

    /**
     * Different placement types a word can take on. When called, this is shuffled so that different words are placed in different manners.
     */
    private val placementType = arrayListOf<PlacementType>(PlacementType.leftRight, PlacementType.rightLeft, PlacementType.upDown, PlacementType.downUp,
        PlacementType.topLeftBottomRight, PlacementType.topRightBottomLeft, PlacementType.bottomLeftTopRight, PlacementType.bottomRightTopLeft)

    /**
     * Return a list of words used to construct the puzzle.
     */
    val usedWordsList = MutableList<Word>(0) { Word("", intArrayOf(0,0), intArrayOf(0,0)) }

    /**
     * Make the grid (2d list).
     * It first makes an empty 2d mutable list, then it places all of the words that can fit in with random placements, anf finally it fills up the empty slots.
     */
    fun makeGrid(size: Int, words: List<String>): List<List<Char>> {
        val grid = MutableList(size) {MutableList<Char>(size) { ' ' } }

        placeAllWords(words, size, grid)
        fillSlots(grid)
//        printGrid(grid)
        return grid
    }

    /**
     * Fills in the empty slots in the grid.
     */
    private fun fillSlots(grid: MutableList<MutableList<Char>>) {
        for (row in grid) {
            val rowIterator = row.listIterator()
            rowIterator.forEach { slot ->
                if (slot == ' ') {
                    rowIterator.set(letters.random())
                }
            }
        }
    }

    /**
     * Prints grid - was used mainly for testing.
     */
    private fun printGrid(grid: MutableList<MutableList<Char>>) {
        for (row in grid) {
            for (slot in row) {
                print(slot)
            }
            println("")
        }
    }

    /**
     * Looks for a slot based on coordinates and movement supplied.
     * If the word fits, and all of the slots it will occupy are either empty or taken by matching characters, it will return true.
     */
    private fun findEmptySection(x: Int, y:Int, word: String, movement: IntArray, grid: MutableList<MutableList<Char>>): Boolean {
        var xPosition = x
        var yPosition = y

        word.forEach { letter ->
            if (xPosition in 0 until grid.size && yPosition in 0 until grid.size) {
                val slot = grid[xPosition][yPosition]
                if (slot == ' ' || slot == letter) {
                    xPosition += movement[0]
                    yPosition += movement[1]
                } else {
                    return false
                }
            } else {
                return false
            }
        }

        return true
    }

    /**
     * If findEmptySection returns true, this places the word in that position on the grid.
     * Given a word and it's placement type, it goes through slot by slot to check if the word can be placed and only stops once that's done, or all possibilities are exhausted.
     * Fills usedWordsList the words, their start and end coordinates on the grid.
     */
    private fun placeWord(word: String, movement: IntArray, size: Int, grid: MutableList<MutableList<Char>>): Boolean {

        val xLength = movement[0]*(word.length)
        val yLength = movement[1]*(word.length)

        val rows = (0..size).shuffled()
        val columns = (0..size).shuffled()

        for (row in rows) {
            for (column in columns) {
                val xFinal = xLength+row
                val yFinal = yLength+column

                if ((xFinal in 0..size) && (yFinal in 0..size)) {
                    if (findEmptySection(column, row, word, movement, grid)) {
                        var xPosition = column
                        var yPosition = row

                        val startPosition = intArrayOf(xPosition, yPosition)

                        word.forEach { letter ->
                            grid[xPosition][yPosition] = letter
                            xPosition += movement[0]
                            yPosition += movement[1]
                        }

                        val finalPosition = intArrayOf(xPosition-movement[0], yPosition-movement[1])
                        usedWordsList.add(Word(word, startPosition, finalPosition))

                        Timber.i("$word begins at (${startPosition[0]}, ${startPosition[1]}) and ends at (${finalPosition[0]}, ${finalPosition[1]}).")

                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * Goes through the different placement types until the word is put into the grid, or all possibilities are exhausted.
     * Returns true if the word was placed in the grid, false if not.
     * The placement types are shuffled for each word.
     * TODO combine with next method
     */
    private fun placeWordList(word: String, size: Int, grid: MutableList<MutableList<Char>>): Boolean {
        val formattedWord = word.toUpperCase()

        for (type in placementType.shuffled()) {
            //TODO Refactor movement function
            if (placeWord(formattedWord, type.movement(type), size, grid)) {
                return true
            }
        }
        return false
    }

    /**
     * Given a shuffled list of words, it tries to place the words into the grid. Returns a list of the used words.
     * Limit set to 6 words per puzzle.
     * TODO combine with previous method
     */
    private fun placeAllWords(words: List<String>, size: Int, grid: MutableList<MutableList<Char>>){
        val shuffledWords = words.shuffled()
        val usedWords = mutableListOf<String>()
        var wordCount = 0

        for (word in shuffledWords) {
            if (placeWordList(word, size, grid)) {
                usedWords.add(word)
                wordCount++
            }
            if (wordCount == 6) {
                break
            }
        }
    }
}