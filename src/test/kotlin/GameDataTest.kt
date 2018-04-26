import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameDataTest {
    @Test
    internal fun throwsError() {
        val move = "test"
        val exception = assertThrows(NumberFormatException::class.java, {
            GameData(arrayListOf("0"), Integer.parseInt(move))
        })

        assertEquals("""For input string: "$move"""", exception.message)
    }
}