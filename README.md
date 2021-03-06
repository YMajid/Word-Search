# Word Search 🔍

Hey! Hope that all is going well 🤠 This is my submission for the Mobile Development Shopify Coding Challenge 🚀

## Requirements 📃
- [X] Create a word search mobile app for Android
- [X] The word search should have at least a 10x10 grid
- [X] Include at least the following 6 words: Swift, Kotlin, ObjectiveC, Variable, Java, Mobile
- [X] Keep track of how many words a user has found
- [X] Make sure it compiles successfully
- [X] Randomize where the words are placed
- [X] Make a slick UI with smooth animations
- [ ] Make it look good in portrait and landscape
- [X] Feel free to add any additional features you can think of
- [X] Allow the user to find the words by swiping over the words

## Architecture 🏗️
Tried to follow the Separation of Concerns design principle. The `Fragment` objects used in this app only display data and capture user and operating system events. On the other hand, the `ViewModel` objects contain all of the data needed for the UI and prepares it for display. Also, `ViewModel` objects handle the operations to be done on the data.

As the `ViewModel` doesn't know about UI components, it isn't affected by configuration changes. However, we still need to communicate information from the `ViewModel` to the `Fragment` so that it updates and redraws the screen. To do this, we use `LiveData`.

`LiveData` is an observable data holder. What this means is that `Fragments` objects can monitor the state of the `LiveData`, so that when a change occurs they are updated.

## Project Structure 🗃️
The project is split into the following components:
- `/custom`
  - contains `GridView` class
  - custom view built to display the puzzle and handle user input
- `/game`
  - contains `GameFragment` and `GameViewModel`
  - gives the view the puzzle to be displayed, keeps track of score and ends the game
- `/main`
  - contains `MainActivity` and `MainApplication`
  - overwrote default `Application` to implement `Timber` logging globaly
- `/score`
  - contains `ScoreFragment`, `ScoreViewModel` and `ScoreViewModelFactory`
  - shows you your final score, gives you the option of playing again and the option to share your score
- `/title`
  - contains `TitleFragment`
  - loading screen for the app
- `/wordPlacement`
  - contains `WordSearch` class, `PlacementType` enum class and `Word` data class
  - deals with the logic to create the word search puzzle

For more information, take a look at the `README.md` file in each directory! 

## The Puzzle 🧩
Each puzzle consists of 6 words picked at random from the list below. Words are placed at random locations. Words are only placed if all the tiles needed are either empty, or the characters match.

Placement types are also chosen at random. Words can be placed from the left to the right, from the right to the left, up, down, diagonally and diagonally backwards.

```
"Kotlin", "Swift", "Java", "ObjectiveC", "Variable", "Mobile", "Shopify", "Fall", "Challenge", "Android", "Engineer", "Intern", "Store", "Culture", "Merchant", "Build", "Business", "Journey", "Market", "Explore", "Invest", "Software", "Powered", "Commerce", "Retail" 
```

## Screenshots 📱
### Title Screen
<p align="left">
  <img alt="" src="screenshots/Screenshot_1588603788.png" width="360" />
</p>


Does not contain too much, just some text and a button to get the game started.

### Game Screen
<p align="left">
  <img alt="" src="screenshots/Screenshot_1588603931.png" width="360" />
</p>

The first screenshot in this section is what you see when the game first starts. It contains the puzzle, list of the words used to construct it, and a minute long timer ticking down.

<p align="left">
  <img alt="" src="screenshots/Screenshot_1588603940.png" width="360" />
</p>

In the second screenshot, you can see what happens when you select a word correctly and what happens when you're still selecting characters. While still selecting characters, their tiles turn yellow until the user's finger is lifted. If the string selected is one of the words used, then the tiles turn blue and a Toast is returned. Otherwise, the tiles turn back to white and a different Toast appears.

<p align="left">
  <img alt="" src="screenshots/Screenshot_1588603956.png" width="360" />
</p>
<p align="left">
  <img alt="" src="screenshots/Screenshot_1588603970.png" width="360" />
</p>

In the third and fourth screenshots, you can see two words being placed diagonally. `INTERN` is going from the top left to the bottom right, while `FALL` is going from the bottom right to the top left (backwards diagonal). Diagonal selection is a bit sensative and as such unwanted tiles are also selected when using the emulator.

<p align="left">
  <img alt="" src="screenshots/Screenshot_1588603983.png" width="360" />
</p>

The fifth screenshot in this section shows characters being reused. `ANDROID` (going from the right to the left) and `ENGINEER` (going from the bottom right to the top left) have an `N` in common. 

### Score Screen
<p align="left">
  <img alt="" src="screenshots/Screenshot_1588777873.png" width="360" />
</p>

Contains the game score (number of words found), a button to play the game again and an items menu to share your score!

<p align="left">
  <img alt="" src="screenshots/Screenshot_1588777226.png" width="360" />
</p>

Here you can see what the different apps are that you can use to share your score.

<p align="left">
  <img alt="" src="screenshots/Screenshot_1588777244.png" width="360" />
</p>

And finally, here you can see what the default message is once you choose to share your score!

If you actually play this game, feel free to send me what your score is at ymajid@uwaterloo.ca 💥