import kotlinx.serialization.json.JSON

class JSONParser {

    fun generateGameData(json: String): GameData {
        return try {
            JSON.parse(GameData.serializer(), json)
        } catch (e: Exception) {
            GameData()
        }
    }
}