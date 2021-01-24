package meaw.meow.pjcoviddashboard.data.repository

import meaw.meow.pjcoviddashboard.data.api.RetrofitInstance

class Repository {

    suspend fun getCoronaSummaryApi() =
        RetrofitInstance.ninjaClient.getCoronaSummary()

    suspend fun getCovidCountriesApi() =
        RetrofitInstance.ninjaClient.getCovidCountries()

    suspend fun getCovidHistoricalApi(country:String) =
            RetrofitInstance.ninjaClient.getCovidHistorical(country)

    suspend fun getCasesSumThailandApi() =
        RetrofitInstance.statThClient.getCasesSumThailand()
}