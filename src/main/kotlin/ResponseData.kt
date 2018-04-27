import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(var board: List<String> = arrayListOf(), @Optional var errors: MutableList<String> = arrayListOf())