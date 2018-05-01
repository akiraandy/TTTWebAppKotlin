import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(var board: List<String> = arrayListOf(),
                        @Optional var errors: MutableList<String> = arrayListOf(),
                        @Optional var gameOver: Boolean = false,
                        @Optional var tied: Boolean = false,
                        @Optional var winner: String? = null,
                        @Optional var currentPlayer: String = "X")