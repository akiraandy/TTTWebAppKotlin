import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GameDataTest {
    @Test
    internal fun throwsError() {
        val input = "test"
        val exception = assertThrows(NumberFormatException::class.java, {
            GameData(arrayListOf("0"), Integer.parseInt(input))
        })

        assertEquals("""For move string: "$input"""", exception.message)
    }
}