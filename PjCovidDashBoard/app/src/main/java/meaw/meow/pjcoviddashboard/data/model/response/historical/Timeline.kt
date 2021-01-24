package meaw.meow.pjcoviddashboard.data.model.response.historical

import com.google.gson.JsonObject

data class Timeline(
    val cases: JsonObject,
    val deaths: JsonObject,
    val recovered: JsonObject
)