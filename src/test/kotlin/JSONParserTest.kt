import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class JSONParserTest {

    @Serializable
    data class JSON_MOCK (val board: ArrayList<String>, val move: Int)

    val jsonParser: JSONParser = JSONParser()
    private val boardArray: ArrayList<String> = arrayListOf("0", "1", "2", "3", "4", "5", "6", "7", "8")
    private val move: Int = 4
    private var json: String = ""


    @BeforeEach
    fun setUp() {
        json = JSON.stringify(JSON_MOCK(boardArray, move))
    }

    @Test
    internal fun returnsDataClass() {
        val gameData = GameData(boardArray, move)
        assertEquals(gameData.toString(), jsonParser.generateGameData(json).toString())
    }
}