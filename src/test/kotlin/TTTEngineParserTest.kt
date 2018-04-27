import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TTTEngineParserTest {

    private val emptyBoardHumanMove: GameData = GameData(arrayListOf("0", "1", "2", "3", "4", "5", "6", "7", "8"), 4)
    private val invalidInput: GameData = GameData(arrayListOf("X", "1", "2", "3", "4", "5", "6", "7", "8"), 0)
    private val computerMakeMove: GameData = GameData(arrayListOf("X", "1", "2", "3", "4", "5", "6", "7", "8"))
    private val computerFullBoard: GameData = GameData(arrayListOf("X", "X", "X", "X", "X", "X", "O", "O", "O"))
    private val tttEngineParser = TTTEngineParser()

    @Test
    internal fun returnsANewBoard() {
        val expected: List<String> = arrayListOf("0", "1", "2", "3", "X", "5", "6", "7", "8")
        val newBoard = tttEngineParser.generateBoard(emptyBoardHumanMove.board, emptyBoardHumanMove.move)
        assertEquals(expected, newBoard)
    }

    @Test
    internal fun computerTurnReturnsNewBoard() {
        val expected: List<String> = arrayListOf("X", "1", "2", "3", "O", "5", "6", "7", "8")
        val newBoard = tttEngineParser.generateBoard(computerMakeMove.board, null)
        assertEquals(expected, newBoard)
    }

    @Test
    internal fun computerMoveReturnsSameBoardIfGameIsOver() {
        val expected: List<String> = arrayListOf("X", "X", "X", "X", "X", "X", "O", "O", "O")
        val newBoard = tttEngineParser.generateBoard(computerFullBoard.board, null)
        assertEquals(expected, newBoard)
    }

    @Test
    internal fun returnsSameBoardIfHumanMakesInvalidInput() {
        val expected: List<String> = arrayListOf("X", "1", "2", "3", "4", "5", "6", "7", "8")
        val newBoard = tttEngineParser.generateBoard(invalidInput.board, invalidInput.move)
        assertEquals(expected, newBoard)
    }
}