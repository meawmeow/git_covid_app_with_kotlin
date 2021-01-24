package meaw.meow.pjcoviddashboard.utils

import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponse
import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponseItem
import java.text.DecimalFormat



fun formatNumber(name:String,format:String):String{
    val dec = DecimalFormat("#,###,###,###,###,###,###")
    val number = dec.format(format.toInt())
    return  name+""+number

}
fun formatNumber(format:String):String{
    val dec = DecimalFormat("#,###,###,###,###,###,###")
    val number = dec.format(format.toInt())
    return number

}

fun getHexColor(_color:Int):String{
    return "#"+Integer.toHexString(_color).substring(2).toString()
}

@Suppress("UNCHECKED_CAST")
fun <T> ArrayList<T>.sortedWithToCountries(comparator: Comparator<in T>, isReversed: Boolean): CountriesResponse {
    val countriesResponse = CountriesResponse()
    var newSort = if (isReversed) {
        this.sortedWith(comparator).reversed() as List<CountriesResponseItem>
    } else {
        this.sortedWith(comparator) as List<CountriesResponseItem>
    }

    newSort.let {
        countriesResponse.addAll(it)
    }
    return countriesResponse
}

fun <T> ArrayList<T>.filterCondition(condition: (T) -> Boolean): ArrayList<T>{
    val result = arrayListOf<T>()
    for (item in this){
        if (condition(item)){
            result.add(item)
        }
    }

    return result
}


