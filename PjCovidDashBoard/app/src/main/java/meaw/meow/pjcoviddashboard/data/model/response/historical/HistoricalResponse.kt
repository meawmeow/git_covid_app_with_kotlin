package meaw.meow.pjcoviddashboard.data.model.response.historical

data class HistoricalResponse(
    val country: String,
    val province: List<String>,
    val timeline: Timeline
)