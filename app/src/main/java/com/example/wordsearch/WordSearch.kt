import com.example.wordsearch.PlacementType

class WordSearch() {
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
        "Android",
        "Engineer",
        "Intern",
        "Store",
        "Culture",
        "Merchant",
        "Business",
        "Journey",
        "Invest"
    )

    val gridSize = 10

    fun makeGrid(size: Int) {
        val grid = MutableList(size) {MutableList<Char>(size) { ' ' } }

        placeAllWords(wordList, size, grid)
        fillSlots(grid)
        printGrid(grid)
    }

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

    fun findSlot(x: Int, y:Int, word: String, movement: IntArray, grid: MutableList<MutableList<Char>>): Boolean {
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

    fun tryPlacingWord(word: String, movement: IntArray, size: Int, grid: MutableList<MutableList<Char>>): Boolean {

        val xLength = movement[0]*(word.length)
        val yLength = movement[1]*(word.length)

        val rows = (0..size).shuffled()
        val columns = (0..size).shuffled()

        for (row in rows) {
            for (column in columns) {
                val xFinal = xLength+row
                val yFinal = yLength+column

                if ((xFinal in 0..size) && (yFinal in 0..size)) {
                    if (findSlot(column, row, word, movement, grid)) {
                        var xPosition = column
                        var yPosition = row
                        word.forEach { letter ->
                            grid[xPosition][yPosition] = letter
                            xPosition += movement[0]
                            yPosition += movement[1]
                        }
                        return true
                    }
                }
            }
        }
        return false
    }

    val placementType = arrayListOf<PlacementType>(PlacementType.leftRight, PlacementType.rightLeft, PlacementType.upDown, PlacementType.downUp,
        PlacementType.topLeftBottomRight, PlacementType.topRightBottomLeft, PlacementType.bottomLeftTopRight, PlacementType.bottomRightTopLeft)

    fun placeWord(word: String, size: Int, grid: MutableList<MutableList<Char>>): Boolean {
        val formattedWord = word.toUpperCase()

        for (type in placementType.shuffled()) {
            //TODO Refactor movement function
            if (tryPlacingWord(formattedWord, type.movement(type), size, grid)) {
                return true
            }
        }
        return false
    }

    //TODO Refactor function names and signatures
    fun placeAllWords(words: List<String>, size: Int, grid: MutableList<MutableList<Char>>): List<String> {
        val shuffledWords = words.shuffled()

        val usedWords = mutableListOf<String>()

        for (word in shuffledWords) {
            if (placeWord(word, size, grid)) {
                usedWords.add(word)
            }
        }

        return usedWords
    }
}