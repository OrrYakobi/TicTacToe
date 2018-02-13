# TicTacToe
Tic-Tac-Toe game written in Java, playable through the command line.

## Requirements
Make sure that you have Java installed on your machine. To do so, run
```
>> java -version
```
If you do not have Java installed, you can install it [here](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html). 

## Setup
1. Clone the project to your machine
2. From within the project directory, compile the program
```
>> javac TicTacToe.java
```
3. And to play the game
```
>> java TicTacToe
```

## How to Play
The Tic-Tac-Toe board is setup with positions 1 through 9 depicted below:

```
 1|2|3
 ----- 
 4|5|6
 -----
 7|8|9
```
 
 The game will prompt users to enter positions where their moves will be made.
 The first player will be o's and the second player will be x's. The game will
 validate that the player has inputted a valid position (1 through 9) that isn't
 occupied. When the game is over by either win or draw, the user will be prompted
 to play again. If they choose to do so, the game will start over.
