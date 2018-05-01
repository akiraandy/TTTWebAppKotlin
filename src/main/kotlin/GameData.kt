import kotlinx.serialization.Serializable

@Serializable
data class GameData(val board: List<String>, var move: Int? = null)
