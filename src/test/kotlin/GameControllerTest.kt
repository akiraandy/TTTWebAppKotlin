import http.request.HttpRequest
import http.response.HttpResponse
import http.status.Status
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameControllerTest {


    private val request: HttpRequest = HttpRequest("POST /move HTTP/1.1\n")
    private val jsonForGameOver = """{"board": ["X", "X", "2", "3", "O", "5", "6", "7", "8"],"move": 2}""""
    private val jsonForInvalidMoveInput: String = """{"board": ["X", "O", "2", "3", "4", "5", "6", "7", "8"],"move":"test"}"""
    private val jsonForInvalidMove: String = """{"board": ["X", "O", "2", "3", "4", "5", "6", "7", "8"],"move": 1}"""
    private val emptyJSON: String = "{}"
    private val emptyJSONAndBadInput: String = """{"move": "test"}"""
    private val computerMove = """"board":["X","1","2","3","O","5","6","7","8"]"""
    private val boardAndError = """"board":["X","O","2","3","4","5","6","7","8"],"errors":["Space already occupied"]"""
    private val invalidMove = """"errors":["Invalid Move"]"""
    private val boardRequired = """"board":[],"errors":["Board required"]"""
    private val errorsForMissingBoardAndBadInput = """"errors":["Board required","Invalid Move"]"""
    private var response: HttpResponse = HttpResponse()
    private val gameOver = """"gameOver":true"""

    @Serializable
    data class MockData(val board: Array<String> = arrayOf("X", "1", "2", "3", "4", "5", "6", "7", "8"), val move: Int? = null)

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        request.body = JSON.stringify(MockData()).toByteArray()
        response = GameController().generateResponse(request)
    }

    @Test
    internal fun returns200() {
        assertEquals(Status.OK, response.status)
    }

    @Test
    internal fun returns422IfError() {
        request.body = jsonForInvalidMove.toByteArray()
        response = GameController().generateResponse(request)
        assertEquals(Status.Unprocessable_Entity, response.status)
    }

    @Test
    internal fun returnsComputerMoveIfThereIsNoMove() {
        assertTrue(response.bodyAsString.contains(computerMove))
    }

    @Test
    internal fun returnsBoardAndErrorIfPlayerMakesInvalidMove() {
        request.body = jsonForInvalidMove.toByteArray()
        response = GameController().generateResponse(request)
        assertTrue(response.bodyAsString.contains(boardAndError))
    }

    @Test
    internal fun errorsIncludeInvalidMoveIfInvalidMoveInputGiven() {
        request.body = jsonForInvalidMoveInput.toByteArray()
        response = GameController().generateResponse(request)
        assertTrue(response.bodyAsString.contains(invalidMove))
    }

    @Test
    internal fun tellsUserBoardIsRequiredIfNoBoardPropertyPresent() {
        request.body = emptyJSON.toByteArray()
        response = GameController().generateResponse(request)
        assertTrue(response.bodyAsString.contains(boardRequired))
    }

    @Test
    internal fun correctErrorsForMissingBoardAndBadMoveInput() {
        request.body = emptyJSONAndBadInput.toByteArray()
        response = GameController().generateResponse(request)
        assertTrue(response.bodyAsString.contains(errorsForMissingBoardAndBadInput))
    }

    @Test
    internal fun hasGameOverField() {
        request.body = jsonForGameOver.toByteArray()
        response = GameController().generateResponse(request)
        assertTrue(response.bodyAsString.contains(gameOver))
    }
}