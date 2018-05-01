# Tic Tac Toe endpoint written in Kotlin

To run:
If you have gradle installed:
```
cd ~/TTTWebAppKotlin
gradle build
gradle run -PappArgs="['-p', '1234', '-d', './']"
```

If you do not have gradle installed:
```
cd ~/TTTWebAppKotlin
./gradlew build
./gradlew run -PappArgs="['-p', '1234', '-d', './']"
```

The number string followed by the '-p' represents the port you want the server to open.

The '-d' followed by the directory represents the working directory you want to set for the server.

# Playing a game

To play a Tic Tac Toe game you must send JSON in a POST request to the ```/move``` route on the server like so:
```
POST /move HTTP/1.1
```

If you want to make a move played by a human, you must include a board and a move represented by an integer like so:
```
{
  "board": ["0", "1", "2", "3", "4", "5", "6", "7", "8"],
  "move": 1
}
```

will return a JSON object that looks like so:
```
{
  "board": ["X", "1", "2", "3", "4", "5", "6", "7", "8"],
  "move": 1
}
```

X always goes first. O always goes second.

To make a computer move you just need to send a valid board:
```
{
  "board": ["X", "1", "2", "3", "4", "5", "6", "7", "8"]
}
```

Will return:
```
{
  "board": ["X", "1", "2", "3", "O", "5", "6", "7", "8"]
}

```

Other properties that will be included in the JSON will include:
```
{
  "board": ["X", "1", "2", "3", "O", "5", "6", "7", "8"],
  "errors": [] // An array of any errors
  "gameOver": false // A boolean that is true if the current board is in a terminal state
  "tie": false // A boolean that is true if the current board represents a tie
  "winner": null // Will return the marker of the winner if there is one (null if there is no winner on the board)
  "currentPlayer": "X" // Will return "X" or "O" depending on who is the current player
}
```
