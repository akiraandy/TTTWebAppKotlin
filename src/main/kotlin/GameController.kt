import clojure.java.api.Clojure
import http.controllers.IController
import http.request.HttpRequest
import http.response.HttpResponse

class GameController : IController {
    override fun generateResponse(request: HttpRequest?): HttpResponse {
        val httpResponse = HttpResponse()
        val vector = Clojure.`var`("clojure.core", "vec")
        val require = Clojure.`var`("clojure.core", "require")
        require.invoke(Clojure.read("clojure-tic-tac-toe.computer"))
        val computerTurn = Clojure.`var`("clojure-tic-tac-toe.computer", "play-turn-computer")
        val map = Clojure.`var`("clojure.core", "hash-map")
        val keyword = Clojure.`var`("clojure.core", "keyword")
        val keywordBoard = keyword.invoke("board")
        val xKeyword = keyword.invoke("x")
        val oKeyword = keyword.invoke("o")
        val currentPlayer = keyword.invoke("current-player")
        val opponentPlayer = keyword.invoke("opponent-player")
        val emptyBoard = vector.invoke(Clojure.read("(:x 1 2 3 4 5 6 7 8)"))
        val gameMap = map.invoke(keywordBoard, emptyBoard, currentPlayer, oKeyword, opponentPlayer, xKeyword)
        val board = computerTurn.invoke(gameMap)
        val stringBuilder = StringBuilder()
        var boardArray = board.toString().removeSurrounding("[", "]").split(" ")
        val iterator = boardArray.iterator()
        while(iterator.hasNext()) {
            val letter = iterator.next()
            if (letter.startsWith(":")) {
                stringBuilder.append(letter.drop(1).capitalize())
            } else {
                stringBuilder.append(letter)
            }
            if (iterator.hasNext()) {
                stringBuilder.append(" ")
            }
        }
        println(stringBuilder.toString().split(" "))
        httpResponse.body = "{board: ${stringBuilder.split(" ")}}".toByteArray()
        return httpResponse
    }
}