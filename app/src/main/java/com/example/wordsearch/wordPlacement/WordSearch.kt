import com.example.wordsearch.wordPlacement.PlacementType
import com.example.wordsearch.wordPlacement.Word
import timber.log.Timber

/**
 * Class used to create the puzzle.
 * Creates a grid with 6 words randomly chosen from a list passed into the createGrid method.
 */
class WordSearch {

    /**
     * All letters of the alphabet.
     */
    private val letters = "ABCDEFGHIJKLMOPQRSTUVWSYZ"

    /**
     * Different placement types a word can take on. When called, this is shuffled so that different words are placed in different manners.
     */
    private val placementTypes = arrayListOf<PlacementType>(
        PlacementType.leftRight,
        PlacementType.rightLeft,
        PlacementType.upDown,
        PlacementType.downUp,
        PlacementType.topLeftBottomRight,
        PlacementType.topRightBottomLeft,
        PlacementType.bottomLeftTopRight,
        PlacementType.bottomRightTopLeft
    )

    /**
     * Return a list of words used to construct the puzzle.
     */
    val usedWordsList = MutableList<Word>(0) {
        Word(
            "",
            false
        )
    }

    /**
     * Make the grid (2d list).
     * It first makes an empty 2d mutable list, then it places all of the words that can fit in with random placements, and finally it fills up the empty slots.
     */
    fun createGrid(size: Int, words: List<String>): List<List<Char>> {
        val grid = MutableList(size) { MutableList<Char>(size) { ' ' } }

        placeWordList(words, size, grid)
        fillSlots(grid)
        Timber.i("The grid is built.")
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
     * Looks for a slot based on coordinates and movement supplied.
     * If the word fits (e.g. all of the slots it will occupy are either empty or taken by matching characters), it will return true.
     */
    private fun findEmptySection(
        x: Int,
        y: Int,
        word: String,
        movement: IntArray,
        grid: MutableList<MutableList<Char>>
    ): Boolean {
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
     * Tries to insert word into a random position on the grid.
     * If findEmptySection returns true, this places the word in that position on the grid.
     * Given a word and it's placement type, it goes through slot by slot to check if the word can be placed and only stops once that's done, or all possibilities are exhausted.
     * Fills usedWordsList the words, their start and end coordinates on the grid.
     */
    private fun placeWord(word: String, movement: IntArray, size: Int, grid: MutableList<MutableList<Char>>): Boolean {

        val xLength = movement[0] * (word.length)
        val yLength = movement[1] * (word.length)

        val rows = (0..size).shuffled()
        val columns = (0..size).shuffled()

        for (row in rows) {
            for (column in columns) {
                val xFinal = xLength + row
                val yFinal = yLength + column

                if ((xFinal in 0..size) && (yFinal in 0..size)) {
                    if (findEmptySection(column, row, word, movement, grid)) {
                        var xPosition = column
                        var yPosition = row

                        word.forEach { letter ->
                            grid[xPosition][yPosition] = letter
                            xPosition += movement[0]
                            yPosition += movement[1]
                        }

                        usedWordsList.add(Word(word, false))

                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * Given a list of words, it tries to place the words into the grid.
     * Goes through the different placement types until the word is put into the grid, or all possibilities are exhausted.
     * The word list and placement types both are shuffled.
     * Limit set to 6 words per puzzle.
     */
    private fun placeWordList(wordList: List<String>, size: Int, grid: MutableList<MutableList<Char>>) {
        val shuffledWordList = wordList.shuffled()
        var wordCount = 0

        for (word in shuffledWordList) {
            val formattedWord = word.toUpperCase()
            val shuffledPlacementTypes = placementTypes.shuffled()
            for (placementType in shuffledPlacementTypes) {
                if (placeWord(formattedWord, placementType.movement(placementType), size, grid)) {
                    wordCount++
                    break
                }
            }
            if (wordCount == 6) {
                Timber.i("The 6 words are placed.")
                break
            }
        }
    }
}