package meaw.meow.pjcoviddashboard.data.model

import meaw.meow.pjcoviddashboard.data.model.response.statthcasesum.Gender

data class DataChart(
    var gender: Gender,
    var dayUpdate:String,
    var valueBarList: ArrayList<Int> = arrayListOf(),
    var nameBarList: ArrayList<String> = arrayListOf()
) {
}