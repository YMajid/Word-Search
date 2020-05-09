# Word Placement 💬

## Word
Data class which holds the word and whether it has been found.

## WordSearch
Contains all of the methods necessary to make the puzzle
- `placeWordList` takes in a list of words and a list of placement types, shuffles both of them and passes it into `placeWord`
- `placeWord` chooses random coordinates and it passes that as well as the word into `findEmptySection`. If it returns true, then it places the word starting at those coordinates and the placement type passed into it
- `findEmptySection` checks if word can fit given the start coordinates and placement type
- `fillSlots` fills in the empty slots in the grid after the 6 words are placed
- `movement` returns the movement associated with each placement type

## PlacementType
Enum class containing all of the different possible placement types.