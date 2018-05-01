import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TTTEngineParserTest {

    private val emptyBoardHumanMove: GameData = GameData(arrayListOf("0", "1", "2", "3", "4", "5", "6", "7", "8"), 4)
    private val invalidInput: GameData = GameData(arrayListOf("X", "1", "2", "3", "4", "5", "6", "7", "8"), 0)
    private val computerMakeMove: GameData = GameData(arrayListOf("X", "1", "2", "3", "4", "5", "6", "7", "8"))
    private val computerFullBoard: GameData = GameData(arrayListOf("X", "X", "X", "X", "X", "X", "O", "O", "O"))
    private val X: String = "X"
    private val O: String = "O"
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

    @Test
    internal fun returnsTrueIfThereIsAWinner() {
        val expected: List<String> = arrayListOf("X", "X", "X", "3", "4", "5", "6", "7", "8")
        val result = tttEngineParser.isThereWinner(expected)
        assertTrue(result)
    }

    @Test
    internal fun returnsFalseIfThereIsNoWinner() {
        val expected: List<String> = arrayListOf("X", "X", "O", "3", "4", "5", "6", "7", "8")
        val result = tttEngineParser.isThereWinner(expected)
        assertFalse(result)
    }

    @Test
    internal fun returnsTrueIfThereIsATie() {
        val expected: List<String> = arrayListOf("X", "X", "O", "O", "O", "X", "X", "O", "X")
        val result = tttEngineParser.isThereTie(expected)
        assertTrue(result)
    }

    @Test
    internal fun returnsFalseIfThereIsNotATie() {
        val expected: List<String> = arrayListOf("X", "X", "O", "3", "4", "5", "6", "7", "8")
        val result = tttEngineParser.isThereTie(expected)
        assertFalse(result)
    }

    @Test
    internal fun returnsTrueIfGameIsOver() {
        val expected: List<String> = arrayListOf("X", "X", "O", "O", "O", "X", "X", "O", "X")
        val result = tttEngineParser.isGameOver(expected)
        assertTrue(result)
    }

    @Test
    internal fun returnsFalseIfGameIsNotOver() {
        val expected: List<String> = arrayListOf("0", "X", "O", "O", "O", "X", "X", "O", "X")
        val result = tttEngineParser.isGameOver(expected)
        assertFalse(result)
    }

    @Test
    internal fun returnsTheMarkerOfTheWinner() {
        var board: List<String> = arrayListOf("X", "X", "X", "3", "4", "5", "6", "7", "8")
        var result = tttEngineParser.getWinner(board)
        assertEquals(X, result)
        board = arrayListOf("O", "O", "O", "3", "4", "5", "6", "7", "8")
        result = tttEngineParser.getWinner(board)
        assertEquals(O, result)
    }

    @Test
    internal fun returnsNullIfThereIsNoWinner() {
        var board: List<String> = arrayListOf("0", "1", "2", "3", "4", "5", "6", "7", "8")
        var result = tttEngineParser.getWinner(board)
        assertEquals(null, result)
    }

    @Test
    internal fun returnsCurrentPlayer() {
        var board: List<String> = arrayListOf("0", "1", "2", "3", "4", "5", "6", "7", "8")
        var result: String = tttEngineParser.getCurrentPlayerAsString(board)
        assertEquals("X", result)
    }
}