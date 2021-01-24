package meaw.meow.pjcoviddashboard.data.model.response.statthcasesum

data class DataCaseMapping(
    var provinceList : ArrayList<ProvinceModel>,
    var gender:Gender,
    val UpdateDate: String
)

data class ProvinceModel(
    var key:String,
    var value:Int
)