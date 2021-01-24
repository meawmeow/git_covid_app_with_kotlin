package meaw.meow.pjcoviddashboard.ui.viewmodel

import androidx.lifecycle.*
import meaw.meow.pjcoviddashboard.data.model.DataChart
import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponse
import meaw.meow.pjcoviddashboard.data.model.response.CountriesResponseItem
import meaw.meow.pjcoviddashboard.data.model.response.coronasummary.CoronaSummaryResponse
import meaw.meow.pjcoviddashboard.data.model.response.historical.HistoricalResponse
import meaw.meow.pjcoviddashboard.data.model.response.statthcasesum.*
import meaw.meow.pjcoviddashboard.data.repository.Repository
import meaw.meow.pjcoviddashboard.utils.*
import meaw.meow.pjcoviddashboard.utils.PageProvince.Companion.PageProvinceNext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class MainViewModel(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {



    private val _coronaSummary = MutableLiveData<Resource<CoronaSummaryResponse>>()
    val coronaSummary: LiveData<Resource<CoronaSummaryResponse>>
        get() = _coronaSummary


    private val _countriesResponseItem = MutableLiveData<CountriesResponseItem>()
    val countriesResponseItem: LiveData<CountriesResponseItem>
        get() = _countriesResponseItem


    private val _countries = MutableLiveData<Resource<CountriesResponse>>()

    private val _historical = MutableLiveData<Resource<HistoricalResponse>>()
    val historical: LiveData<Resource<HistoricalResponse>>
        get() = _historical

    private val _caseSumThailad = MutableLiveData<Resource<DataCaseMapping>>()
    val caseSumThailad: LiveData<Resource<DataCaseMapping>>
        get() = _caseSumThailad

    private val _dataChart = MutableLiveData<Resource<DataChart>>()
    val dataChart: LiveData<Resource<DataChart>>
        get() = _dataChart

    //    val countries: LiveData<Resource<CountriesResponse>>
//        get() = _countries
    var countryQuery = MutableLiveData<String>()
    var titlebar = MutableLiveData<String>()

    init {
        //fetchCountries()
        countryQuery.value = null
        titlebar.value = "Home Coronavirus"
    }

    fun setValue(newValue: Resource<CountriesResponse>) {
        if (_countries.value != newValue) {
            _countries.value = newValue
        }
    }

    val countries: LiveData<Resource<CountriesResponse>>
        get() = Transformations.switchMap(countryQuery) { queryStr ->
            val allCountries = _countries
            val newCountries = when {
                queryStr == null -> allCountries
                else -> {
                    Transformations.switchMap(allCountries) { countriesList ->
                        var filteredCountries = MutableLiveData<Resource<CountriesResponse>>()
                        val filteredList = countriesList.data?.filter { data ->
                            data.country.toUpperCase().trim()
                                .contains(queryStr.toUpperCase().trim())
                        }
                        val countriesResponse = CountriesResponse()
                        filteredList?.let { countriesResponse.addAll(it) }
                        filteredCountries.value = Resource.querystr(countriesResponse)
                        filteredCountries

                    }
                }
            }
            newCountries
        }

    fun setSelectedCountriesItem(_countriesItem: CountriesResponseItem) {
        _countriesResponseItem.postValue(_countriesItem)
    }

    fun sortDeathMinList() {
        _countries.value = Resource.querystr(
            _countries.value?.data?.sortedWithToCountries(
                compareBy<CountriesResponseItem> { it.deaths.toInt() }.thenBy { it.cases.toInt() },
                false
            )
        )
    }

    fun sortDeathMaxList() {
        _countries.value = Resource.querystr(
            _countries.value?.data?.sortedWithToCountries(
                compareBy<CountriesResponseItem> { it.deaths.toInt() }.thenBy { it.cases.toInt() },
                true
            )
        )
    }

    fun sortCasesMinList() {
        _countries.value =
            Resource.querystr(_countries.value?.data?.sortedWithToCountries(compareBy<CountriesResponseItem> { it.cases.toInt() }.thenBy { it.todayCases.toInt() },
                false
            )
            )
    }

    fun sortCasesMaxList() {
        _countries.value = Resource.querystr(
            _countries.value?.data?.sortedWithToCountries(
                compareBy<CountriesResponseItem> { it.cases.toInt() }.thenBy { it.todayCases.toInt() },
                true
            )
        )
    }

    fun sortNewCasesMinList() {
        _countries.value = Resource.querystr(
            _countries.value?.data?.sortedWithToCountries(
                compareBy<CountriesResponseItem> { it.todayCases.toInt() }.thenBy { it.cases.toInt() },
                false
            )
        )
    }

    fun sortNewCasesMaxList() {
        _countries.value = Resource.querystr(
            _countries.value?.data?.sortedWithToCountries(
                compareBy<CountriesResponseItem> { it.todayCases.toInt() }.thenBy { it.cases.toInt() },
                true
            )
        )
    }

    fun searchCountry(country: String?) {
        countryQuery.postValue(country)
    }

    fun setTitleBar(strTitle: String?) {
        titlebar.postValue(strTitle)
    }

    fun getItemClick(countriesResponseItem: CountriesResponseItem) {

    }

    fun fetchCoronaSummary() {
        viewModelScope.launch {
            _coronaSummary.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getCoronaSummaryApi().let {
                    if (it.isSuccessful) {
                        _coronaSummary.postValue(Resource.success(it.body()))
                        // Log.d("DSSL", "${it.body()?.javaClass}")
                    } else _coronaSummary.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _coronaSummary.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchCountries() {
        viewModelScope.launch {
            _countries.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getCovidCountriesApi().let {
                    if (it.isSuccessful) {
                        _countries.postValue(Resource.success(it.body()))
                        // Log.d("DSSL", "${it.body()?.javaClass}")
                    } else _countries.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _countries.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fetchHistorical() {
        viewModelScope.launch {
            _historical.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getCovidHistoricalApi(countriesResponseItem.value!!.country).let {
                    if (it.isSuccessful) {
                        _historical.postValue(Resource.success(it.body()))
                    } else {
                        _historical.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _historical.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun fetchCasesSumThai() {
        viewModelScope.launch {
            _caseSumThailad.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                repository.getCasesSumThailandApi().let {
                    if (it.isSuccessful) {


                        var _dataProvince = ArrayList<ProvinceModel>()
                        it.body()?.Province?.let {
                            for (d in it.entrySet()) {
                                _dataProvince.add(
                                    ProvinceModel(
                                        d.key,
                                        d.value.asInt
                                    )
                                )
                            }
                        }

                        var gender = Gender(0, 0, 0)
                        it.body()?.Gender.let {
                            if (it != null) {
                                gender.Female = it.Female
                                gender.Male = it.Male
                                gender.Unknown = it.Unknown

                            }
                        }
                        var dayUpdate = ""
                        it.body()?.UpdateDate.let {
                            dayUpdate = it.toString()
                        }

                        var dataMapping = DataCaseMapping(_dataProvince, gender, dayUpdate)


                        _caseSumThailad.postValue(Resource.success(dataMapping))
                    } else {
                        _caseSumThailad.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _caseSumThailad.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    suspend fun getPageProvinceData(isNext: Boolean = true) = withContext(Dispatchers.IO) {

        caseSumThailad.value?.data?.let {
            _dataChart.postValue(Resource.loading(null))
            var pageData = PageProvinceNext(it.provinceList, isNext)

            var valueBarList: ArrayList<Int> = arrayListOf()
            var nameBarList: ArrayList<String> = arrayListOf()

            for (d in pageData) {
                valueBarList.add(d.value)
                nameBarList.add("${d.key}")
            }
            var dataChart = DataChart(it.gender, it.UpdateDate)
            if (pageData.size > 0) {
                dataChart.nameBarList = nameBarList
                dataChart.valueBarList = valueBarList
                _dataChart.postValue(Resource.success(dataChart))
            } else {
                _dataChart.postValue(Resource.nothing())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        _dataChart.postValue(null)
        _caseSumThailad.postValue(null)
        _historical.postValue(null)
        _countries.postValue(null)
        _countriesResponseItem.postValue(null)
        _coronaSummary.postValue(null)
    }


}