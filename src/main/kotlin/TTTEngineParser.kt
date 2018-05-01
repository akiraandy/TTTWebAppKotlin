import clojure.java.api.Clojure

class TTTEngineParser {
    private val vector = Clojure.`var`("clojure.core", "vec")!!
    private val str = Clojure.`var`("clojure.core", "str")!!
    private val require = Clojure.`var`("clojure.core", "require")!!
    private val turn = Clojure.`var`("clojure-tic-tac-toe.player", "play-turn")!!
    private val winner = Clojure.`var`("clojure-tic-tac-toe.rules", "winner?")!!
    private val gameOver = Clojure.`var`("clojure-tic-tac-toe.rules", "game-over?")!!
    private val getTheWinner = Clojure.`var`("clojure-tic-tac-toe.rules", "get-winner")!!
    private val tie = Clojure.`var`("clojure-tic-tac-toe.rules", "tie?")!!
    private val map = Clojure.`var`("clojure.core", "hash-map")!!
    private val keyword = Clojure.`var`("clojure.core", "keyword")!!
    private val keywordBoard = keyword.invoke("board")!!
    private val inputKeyword = keyword.invoke("input")!!
    private val currentPlayer = keyword.invoke("current-player")!!
    private val opponentPlayer = keyword.invoke("opponent-player")!!

    fun generateBoard(board: List<String>?, move: Int?): List<String> {
        require.invoke(Clojure.read("clojure-tic-tac-toe.player"))
        val boardVector = vector.invoke(Clojure.read(convertStringArrayToListString(board)))
        val gameMap = map.invoke(keywordBoard, boardVector, inputKeyword, move, currentPlayer, getCurrentPlayer(boardVector), opponentPlayer, getOpponentPlayer(boardVector))
        val newBoard = turn.invoke(gameMap)
        return convertVectorToList(newBoard.toString())
    }

    fun getCurrentPlayerAsString(board: List<String>): String {
        val boardVector = vector.invoke(Clojure.read(convertStringArrayToListString(board)))
        val currentPlayer = str.invoke(getCurrentPlayer(boardVector)) as String
        return convertSymbolToChar(currentPlayer)
    }

    fun getWinner(board: List<String>): String? {
        require.invoke(Clojure.read("clojure-tic-tac-toe.rules"))
        val boardVector = vector.invoke(Clojure.read(convertStringArrayToListString(board)))
        val winner = getTheWinner.invoke(boardVector)
        return if (winner != null) convertSymbolToChar(str.invoke(winner) as String) else winner
    }

    fun isGameOver(board: List<String>): Boolean {
        require.invoke(Clojure.read("clojure-tic-tac-toe.rules"))
        val boardVector = vector.invoke(Clojure.read(convertStringArrayToListString(board)))
        return gameOver.invoke(boardVector) as Boolean
    }

    fun isThereWinner(board: List<String>): Boolean {
        require.invoke(Clojure.read("clojure-tic-tac-toe.rules"))
        val boardVector = vector.invoke(Clojure.read(convertStringArrayToListString(board)))
        return winner.invoke(boardVector) as Boolean
    }

    fun isThereTie(board: List<String>): Boolean {
        require.invoke(Clojure.read("clojure-tic-tac-toe.rules"))
        val boardVector = vector.invoke(Clojure.read(convertStringArrayToListString(board)))
        return tie.invoke(boardVector) as Boolean
    }

    private fun convertStringArrayToListString(array: List<String>?): String {
        val stringBuilder = StringBuilder()
        val iterator = array!!.iterator()
        stringBuilder.append("(")
        while (iterator.hasNext()) {
            val ele = convertCharToSymbol(iterator.next())
            stringBuilder.append(ele)
            if (iterator.hasNext()) {
                stringBuilder.append(" ")
            }
        }
        stringBuilder.append(")")
        return stringBuilder.toString()
    }

    private fun convertVectorToList(vector: String): MutableList<String> {
        val newList: MutableList<String> = arrayListOf()
        val split = vector.removeSurrounding("[", "]").split(" ")
        split.forEach {
            newList.add(convertSymbolToChar(it))
        }
        return newList
    }

    private fun convertCharToSymbol(ele: String): String {
        var newString: String = ele
        val symbol = ":"
        val regex = """[a-zA-Z]""".toRegex()
        if (regex matches ele) {
            newString = symbol.plus(ele.toLowerCase())
        }
        return newString
    }

    private fun convertSymbolToChar(ele: String): String {
        return ele.removePrefix(":").toUpperCase()
    }

    private fun getCurrentPlayer(boardVector: Any): Any? {
        require.invoke(Clojure.read("clojure-tic-tac-toe.turn-controller"))
        val currentPlayer = Clojure.`var`("clojure-tic-tac-toe.turn-controller", "current-player")
        return currentPlayer.invoke(boardVector)
    }

    private fun getOpponentPlayer(boardVector: Any): Any? {
        require.invoke(Clojure.read("clojure-tic-tac-toe.turn-controller"))
        val opponentPlayer = Clojure.`var`("clojure-tic-tac-toe.turn-controller", "opponent-player")
        return opponentPlayer.invoke(boardVector)
    }

}
