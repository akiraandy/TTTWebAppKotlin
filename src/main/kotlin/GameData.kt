import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class GameData(val board: ArrayList<String>? = null, @Optional var move: Int? = null)
