package meaw.meow.pjcoviddashboard.adapter

import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponseItem

interface CountriesItemClickListenner {
    fun onCountriesItemClick(countriesResponseItem: CountriesResponseItem)
}