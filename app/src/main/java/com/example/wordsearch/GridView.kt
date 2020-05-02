package com.example.wordsearch

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

/**
 * View used to display the puzzle
 */
class GridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr), View.OnTouchListener {

    init {
        setOnTouchListener(this)
    }

    data class Tile(val row: Int, val column: Int, val character: Char)

    /**
     * Communicates score to the score fragment.
     * TODO look for a better way of doing this
     */
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    var usedWordsList: List<Word> = emptyList()
    var tiles: MutableList<Tile> = mutableListOf()
    private val selectedTiles = mutableSetOf<Tile>()
    private val correctSelectedTiles = mutableSetOf<Tile>()
    private var stringSelection = ""

    private val tilePaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
        style = Paint.Style.STROKE
        textSize = 20f * Resources.getSystem().displayMetrics.scaledDensity
    }

    private var selectedTilePaint = Paint().apply {
        textAlign = Paint.Align.CENTER
//        color = Color.YELLOW
        style = Paint.Style.FILL
        textSize = 20f * Resources.getSystem().displayMetrics.scaledDensity
    }

    private val textPaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
        textSize = 20f * Resources.getSystem().displayMetrics.scaledDensity
    }

    var data: List<List<Char>> = emptyList()
        set(value) {
            field = value
            tiles.clear()
            value.forEachIndexed { row, chars ->
                chars.forEachIndexed { column, char ->
                    tiles.add(Tile(row, column, char))
                }
            }
            invalidate()
        }

    /**
     * Highlights selected tile.
     * Color green if selected string is correct, yellow is still selecting tiles.
     * TODO find better colours
     */
    private fun highlightSelectedTiles(tile: Tile, canvas: Canvas, correct: Boolean) {
        canvas.drawRect(
            tile.column * tileWidth(),
            tile.row * tileHeight(),
            (tile.column + 1) * tileWidth(),
            (tile.row + 1) * tileHeight(),
            selectedTilePaint.apply {
                color = if (correct) {
                    Color.GREEN
                } else {
                    Color.YELLOW
                }
            }
        )
    }

    /**
     * Puts in characters, draws grid and highlights selected strings.
     * TODO make prettier
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        selectedTiles.forEach { tile ->
            highlightSelectedTiles(tile, canvas, false)
        }
        correctSelectedTiles.forEach { tile ->
            highlightSelectedTiles(tile, canvas, true)
        }
        tiles.forEach { tile ->
            canvas.drawRect(
                tile.column * tileWidth(),
                tile.row * tileHeight(),
                (tile.column + 1) * tileWidth(),
                (tile.row + 1) * tileHeight(),
                tilePaint
            )
            canvas.drawText(
                tile.character.toString(),
                horizontalCenterOfTile(tile.column),
                verticalCenterOfTile(tile.row),
                textPaint
            )
        }
    }

    private fun horizontalCenterOfTile(column: Int) = (column * tileWidth()) + tileWidth() / 2
    private fun verticalCenterOfTile(row: Int) = (row * tileHeight()) + tileHeight() / 2

    private fun tileWidth() = width / data.size.toFloat()
    private fun tileHeight() = height / data.size.toFloat()

    private fun coordinatesToTile(x: Float, y: Float): Tile {
        val column = (x / tileWidth()).toInt()
        val row = (y / tileHeight()).toInt()
        return Tile(row, column, data[row][column])
    }

    /**
     * Selects the tiles where the user touched the screen. Once the user lifts their finger, it passes the string
     * selected into the wordFound method.
     * TODO if selected word is wrong, unhighlight right away.
     * TODO fix selecting diagonal words.
     * TODO fix selecting if moving too fast.
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                try {
                    val selectedTile = coordinatesToTile(event.x, event.y)
                    if (selectedTiles.add(selectedTile)) {
                        stringSelection += selectedTile.character
                        invalidate()
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (wordFound(stringSelection)) {
                    selectedTiles.forEach { tile ->
                        correctSelectedTiles.add(tile)
                    }
                }
                selectedTiles.clear()
                stringSelection = ""
            }
        }
        return true
    }

    /**
     * Compares string user selected with the words used to construct the puzzle.
     * If one matches, it changes the word's boolean value to true and increments score by 1.
     * Returns a Toast based on what the word the user inputted.
     */
    private fun wordFound(stringSelected: String): Boolean {
        for (word in usedWordsList) {
            if (!word.found && word.word == stringSelected) {
                word.found = true
                _score.value = _score.value?.plus(1) ?: 1
                val congratulate = correctStringSelected.shuffled().first()
                Timber.i("Word ${word.word} found!")
                Toast.makeText(context, congratulate, Toast.LENGTH_SHORT).show()
                return true
            }
            if (word.found && word.word == stringSelected) {
                val message = foundStringSelected.shuffled().first()
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                return false
            }
        }
        val shame = wrongStringSelected.shuffled().first()
        Toast.makeText(context, shame, Toast.LENGTH_SHORT).show()
        return false
    }

    /**
     * List of words/phrases to be returned in the Toast.
     */
    private val correctStringSelected = listOf<String>(
        "SHEEESH",
        "OKAYYYYYY",
        "JHEEEZ",
        "DAYUMMMM",
        "SMART ONE EHH",
        "MY YUTEEE",
        "PREEING IT I SEEEEE"
    )

    private val foundStringSelected = listOf<String>(
        "find something else yo",
        "maybe find another one??",
        "there's other words...",
        "move on g"
    )

    private val wrongStringSelected = listOf<String>(
        "watch your finger",
        "read a dictionary",
        "ur not too good huuuh",
        "big mistake",
        "ok floyd mayweather"
    )
}