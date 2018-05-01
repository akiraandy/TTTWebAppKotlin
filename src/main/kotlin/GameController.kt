import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import http.controllers.IController
import http.method.HttpMethod
import http.request.HttpRequest
import http.response.HttpResponse
import http.status.Status
import kotlinx.serialization.json.JSON
import java.nio.charset.Charset

class GameController : IController {

    private val tttEngineParser: TTTEngineParser = TTTEngineParser()

    private val boardLength = 9
    private val validMove = "[^0-8]"

    override fun generateResponse(request: HttpRequest): HttpResponse {
        return when (request.method()) {
            HttpMethod.OPTIONS -> handleOptions()
            HttpMethod.POST -> handlePost(request)
            else -> {
                HttpResponse()
            }
        }
    }

    private fun handlePost(request: HttpRequest): HttpResponse {
        val httpResponse = HttpResponse()
        val responseData = ResponseData()
        val json: JsonObject = getJsonObject(request, responseData)
        val board = getBoard(json, responseData)
        val move = getMove(json, responseData)
        val gameData = GameData(board, move)

        if (responseData.errors.isEmpty()) {
            responseData.board = tttEngineParser.generateBoard(gameData.board, gameData.move)
            if (responseData.board == gameData.board) responseData.errors.add("Space already occupied")
        } else  {
            responseData.board = gameData.board
        }

        responseData.gameOver = tttEngineParser.isGameOver(responseData.board)
        responseData.tied = tttEngineParser.isThereTie(responseData.board)
        responseData.winner = tttEngineParser.getWinner(responseData.board)
        responseData.currentPlayer = tttEngineParser.getCurrentPlayerAsString(responseData.board)

        httpResponse.status = if (responseData.errors.isEmpty()) Status.OK else Status.Unprocessable_Entity
        httpResponse.setBody(generateBody(responseData))
        httpResponse.addHeader("Content-Type", "application/json")
        httpResponse.addHeader("Access-Control-Allow-Origin", "*")
        return httpResponse
    }

    private fun handleOptions(): HttpResponse {
        val httpResponse = HttpResponse()
        httpResponse.setBody("")
        httpResponse.addHeader("Access-Control-Allow-Origin", "*")
        httpResponse.addHeader("Access-Control-Allow-Methods", "POST, OPTIONS")
        httpResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Accept-Encoding, Accept-Language, Cookie, Referer")
        httpResponse.addHeader("Access-Control-Max-Age", "86400")
        httpResponse.status = Status.OK
        return httpResponse
    }

    private fun getJsonObject(request: HttpRequest, responseData: ResponseData): JsonObject {
        val parser = Parser()
        try {
            val stringBuilder = StringBuilder(request.body.toString(Charset.defaultCharset()))
            return parser.parse(stringBuilder) as JsonObject
        } catch (e: Exception) {
            responseData.errors.add("Invalid JSON")
            return parser.parse(StringBuilder("{}")) as JsonObject
        }
    }

    private fun getBoard(json: JsonObject, responseData: ResponseData): List<String> {
        return when (json["board"]) {
            is JsonArray<*> -> {
                val boardList = convertJSONArrayToList(json["board"] as JsonArray<String>)
                if (boardList.size != boardLength)
                    responseData.errors.add("Invalid board length")
                boardList
            }
            is Any -> {
                responseData.errors.add("Invalid Board")
                arrayListOf()
            }
            else -> {
                responseData.errors.add("Board required")
                arrayListOf()
            }
        }
    }

    private fun convertJSONArrayToList(jsonArray: JsonArray<String>): List<String> {
        val list = mutableListOf<String>()
        jsonArray.forEach {
            list.add(it)
        }
        return list
    }

    private fun getMove(json: JsonObject, responseData: ResponseData): Int? {
        val regex = Regex(validMove)
        val move = json["move"]
        if (move != null && regex.containsMatchIn(move.toString())) {
            responseData.errors.add("Invalid Move")
        }
        return move.toString().toIntOrNull()
    }

    private fun generateBody(responseData: ResponseData): String {
        return JSON.stringify(responseData)
    }
}