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
        val gameData = jsonParse.generateGameData(request.body.toString(Charset.defaultCharset()))
        try {
            val board = tttEngineParser.generateBoard(gameData.board, gameData.input)
            httpResponse.status = Status.OK
            httpResponse.setBody(generateBody(board))
        } catch (e: Exception) {
            httpResponse.status = Status.Unprocessable_Entity
            httpResponse.setBody("""{ "Error" : "Invalid Move" }""")
            httpResponse.addHeader("Content-Type", "application/json")
        }
        return httpResponse
    }

    private fun generateBody(board: List<String>): String {
        return "{board: [${board.joinToString { it }}]}"
    }
}