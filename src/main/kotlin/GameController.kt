import http.controllers.IController
import http.request.HttpRequest
import http.response.HttpResponse
import http.status.Status
import kotlinx.serialization.json.JSON
import java.nio.charset.Charset

class GameController : IController {

    private val tttEngineParser: TTTEngineParser = TTTEngineParser()
    private val jsonParse: JSONParser = JSONParser()
    private val httpResponse = HttpResponse()
    private val boardLength = 9

    override fun generateResponse(request: HttpRequest): HttpResponse {
        var gameData = GameData(arrayListOf())
        var responseData = ResponseData()
        try {
            gameData = jsonParse.generateGameData(request.body.toString(Charset.defaultCharset()))
        } catch (e: NumberFormatException) {
            responseData.errors.add("Invalid Move")
        } catch (e: IllegalArgumentException) {
            responseData.errors.add("Invalid Board")
        } catch (e: kotlinx.serialization.MissingFieldException) {
            responseData.errors.add("Board required")
        }

        if (gameData.board!!.size == boardLength) {
            responseData.board = tttEngineParser.generateBoard(gameData.board, gameData.move)
        } else  {
            responseData.board = gameData.board
        }

        checkBoard(gameData.board!!, responseData.board!!, responseData)
        httpResponse.status = if (responseData.errors.isEmpty()) Status.OK else Status.Unprocessable_Entity
        httpResponse.setBody(generateBody(responseData))
        httpResponse.addHeader("Content-Type", "application/json")
        return httpResponse
    }

    private fun generateBody(responseData: ResponseData): String {
        return JSON.stringify(responseData)
    }

    private fun checkBoard(requestBoard: List<String>, generatedBoard: List<String>, responseData: ResponseData) {
        if (generatedBoard.size != boardLength) responseData.errors.add("Invalid board size")
        if (generatedBoard == requestBoard && !responseData.errors.contains("Invalid board size")) responseData.errors.add("Space already occupied")
    }

}