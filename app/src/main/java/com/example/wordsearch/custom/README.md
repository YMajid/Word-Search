# Custom 🛃

This directory contains the custom view used to display the puzzle, `GridView`.

## Grid View
The view takes in a nested array of characters and displayes it on the screen. Each character gets its own "tile" on the grid. In order to detect which tiles the user touched on the screen, we extend the `View.OnTouchListener` interface.

The `onTouch` method is overwritten such that when the user first touches the screen, it starts recording what characters the user is swiping over. Once the user's finger is lifted from the screen, the string selected is passed into the `wordFound` method to check if it is one of the words that's a part of the puzzle. While the user is selecting the cells, they're being highlighted in a yellow colour.

If the string passed into the `wordFound` method is a part of the puzzle and has not already been found, then the score is incremented by one and the tiles are highlighted in a blue colour. If the string passed into the method corresponds to a word already found or not a part of the puzzle, the score does not change and no tiles are highlighted. In all cases, a small 🍞 is returned.

A `score` live data is being used here to keep track of the score, and so that it can be passed back into the `GameViewModel`.