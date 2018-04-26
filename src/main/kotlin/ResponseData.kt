import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
data class ResponseData(@Optional var board: List<String>? = null, @Optional var errors: MutableList<String> = arrayListOf())