package meaw.meow.pjcoviddashboard.data.api

import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponse
import meaw.meow.pjcoviddashboard.data.model.response.coronasummary.CoronaSummaryResponse
import meaw.meow.pjcoviddashboard.data.model.response.historical.HistoricalResponse
import meaw.meow.pjcoviddashboard.data.model.response.statthcasesum.CaseSumThailadResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("all")
    suspend fun getCoronaSummary(): Response<CoronaSummaryResponse>

    @GET("countries")
    suspend fun getCovidCountries(): Response<CountriesResponse>

    @GET("historical/{country}")
    suspend fun getCovidHistorical(@Path("country") country:String) : Response<HistoricalResponse>

    @GET("api/open/cases/sum")
    suspend fun getCasesSumThailand(): Response<CaseSumThailadResponse>
}