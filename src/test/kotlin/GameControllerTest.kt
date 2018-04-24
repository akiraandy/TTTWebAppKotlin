import http.request.HttpRequest
import http.response.HttpResponse
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameControllerTest {
    private val request: HttpRequest = HttpRequest("GET /move HTTP/1.1\n")
    private val response: HttpResponse = GameController().generateResponse(request)

    @Serializable
    data class MockBody(val board: Array<String> = arrayOf("X", "1", "2", "3", "4", "5", "6", "7", "8"), @Optional val move: Int = -1)

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        request.body = JSON.stringify(MockBody()).toByteArray()
    }

    @Test
    internal fun returnsComputerMoveIfThereIsNoMove() {
        assertEquals("{board: [X, 1, 2, 3, O, 5, 6, 7, 8]}", response.bodyAsString)
    }
}