import http.controllers.IController
import http.request.HttpRequest
import http.response.HttpResponse
import http.status.Status
import java.nio.charset.Charset

class GameController : IController {

    private val tttEngineParser: TTTEngineParser = TTTEngineParser()
    private val jsonParse: JSONParser = JSONParser()

    override fun generateResponse(request: HttpRequest): HttpResponse {
        val httpResponse = HttpResponse()
        try {
            val gameData = jsonParse.generateGameData(request.body.toString(Charset.defaultCharset()))
            try {
                val board = tttEngineParser.generateBoard(gameData.board, gameData.move)
                httpResponse.status = Status.OK
                httpResponse.setBody(generateBody(board))
            } catch (e: InvalidBoardLengthException) {
                httpResponse.status = Status.Unprocessable_Entity
                httpResponse.setBody("""{ "Error" : "Invalid board length" }""")
            }
        } catch (e: NumberFormatException) {
            httpResponse.status = Status.Unprocessable_Entity
            httpResponse.setBody("""{ "Error" : "Invalid Move" }""")
        } catch (e: IllegalArgumentException) {
            httpResponse.status = Status.Unprocessable_Entity
            httpResponse.setBody("""{ "Error" : "Invalid Board" }""")
        } catch (e: kotlinx.serialization.MissingFieldException) {
            httpResponse.status = Status.Unprocessable_Entity
            httpResponse.setBody("""{ "Error" : "Board required" }""")
        }
        httpResponse.addHeader("Content-Type", "application/json")
        return httpResponse
    }

    private fun generateBody(board: List<String>): String {
        return "{board: [${board.joinToString { it }}]}"
    }
}