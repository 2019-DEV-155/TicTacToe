# Tic Tac Toe

## About this repo

This is a short and simple Tic Tac Toe in Android

## Rules

The rules are described below :

- X always goes first.
- Players cannot play on a played position.
- Players alternate placing X’s and O’s on the board until either:
	- One player has three in a row, horizontally, vertically or diagonally
	- All nine squares are filled.
- If a player is able to draw three X’s or three O’s in a row, that player wins.
- If all nine squares are filled and neither player has three in a row, the game is a draw.

## How to run the tests

### Command line
 
On GNU/Linux or Mac OS :
```bash
./gradlew testDebug
```

On Windows :
```
gradlew.bat testDebug
```

### With Android Studio

Open the project in Android Studio 3.4+, open the file [GameTest.kt](tictactoe/src/test/java/com/dev2019155/tictactoe/GameTest.kt)

Click on the Arrow next to the class name (`class GameTest`)

## How to run the program

First, plug the device or launch the emulator on which you wish to install this application

### Command line

On GNU/Linux or Mac OS :
```bash
./gradlew installDebug
```

On Windows :
```
gradlew.bat installDebug
```

Then open the app called "TicTacToe" on your phone/emulator

### With Android Studio

Open the project in Android Studio 3.4+, then click on the play icon.