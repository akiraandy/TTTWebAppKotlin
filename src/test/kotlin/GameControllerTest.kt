import http.request.HttpRequest
import http.response.HttpResponse
import http.status.Status
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameControllerTest {
    private val request: HttpRequest = HttpRequest("GET /move HTTP/1.1\n")
    private val jsonResponse: String = "{board: [X, 1, 2, 3, O, 5, 6, 7, 8]}"
    private var response: HttpResponse = HttpResponse()

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
    internal fun returnsComputerMoveIfThereIsNoMove() {
        assertEquals(jsonResponse, response.bodyAsString)
    }
}