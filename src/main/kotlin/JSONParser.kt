import kotlinx.serialization.json.JSON

class JSONParser {

    fun generateGameData(json: String): GameData {
        return JSON.nonstrict.parse(GameData.serializer(), json)
    }
}