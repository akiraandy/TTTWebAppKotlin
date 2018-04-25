import clojure.java.api.Clojure

class TTTEngineParser {
    private val vector = Clojure.`var`("clojure.core", "vec")!!
    private val require = Clojure.`var`("clojure.core", "require")!!
    private val turn = Clojure.`var`("clojure-tic-tac-toe.player", "play-turn")!!
    private val map = Clojure.`var`("clojure.core", "hash-map")!!
    private val keyword = Clojure.`var`("clojure.core", "keyword")!!
    private val keywordBoard = keyword.invoke("board")!!
    private val inputKeyword = keyword.invoke("input")!!
    private val currentPlayer = keyword.invoke("current-player")!!
    private val opponentPlayer = keyword.invoke("opponent-player")!!

    fun generateBoard(board: List<String>?, input: Int?): List<String> {
        require.invoke(Clojure.read("clojure-tic-tac-toe.player"))
        val boardVector = vector.invoke(Clojure.read(convertStringArrayToListString(board)))
        val gameMap = map.invoke(keywordBoard, boardVector, inputKeyword, input, currentPlayer, getCurrentPlayer(boardVector), opponentPlayer, getOpponentPlayer(boardVector))
        val newBoard = turn.invoke(gameMap)
        return convertVectorToList(newBoard.toString())
    }

    private fun convertStringArrayToListString(array: List<String>?): String {
        val stringBuilder = StringBuilder()
        val iterator = array!!.iterator()
        stringBuilder.append("(")
        while(iterator.hasNext()) {
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