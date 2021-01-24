package meaw.meow.pjcoviddashboard.data.model.response.statthcasesum

import com.google.gson.JsonObject

data class CaseSumThailadResponse(
    val DevBy: String,
    val Gender: Gender,
    val LastData: String,
    val Nation: JsonObject,
    val Province: JsonObject,
    val SeverBy: String,
    val Source: String,
    val UpdateDate: String
)