package meaw.meow.pjcoviddashboard.utils

import meaw.meow.pjcoviddashboard.data.model.response.statthcasesum.ProvinceModel

class PageProvince {

    companion object{

        var PAGE_DATA_SIZE = 15
        var maxPages: Int = 1
        var _currentPage = 0
        val adderList : MutableList<ProvinceModel> = arrayListOf()
        var beginPage = 0
        var engPage = 0
        fun PageProvinceNext(_dataProvince : ArrayList<ProvinceModel>,isNext:Boolean = true) : MutableList<ProvinceModel>{
            adderList.clear()

            if (_dataProvince.size % PAGE_DATA_SIZE == 0) {
                maxPages = _dataProvince.size / PAGE_DATA_SIZE
            } else {
                maxPages = _dataProvince.size / PAGE_DATA_SIZE + 1
            }

            if(isNext){
                _currentPage+=1
            }else{
                _currentPage -= 1
            }

            try {
                if(_currentPage==0){
                    _currentPage = 1
                }
                if(_currentPage >= maxPages){
                    _currentPage = maxPages
                }

                val _feedCount = _currentPage * PAGE_DATA_SIZE

                if (_feedCount <= (_dataProvince.size)) {
                    beginPage = _feedCount - (PAGE_DATA_SIZE)
                    engPage = _feedCount
                    var tmpList = _dataProvince.subList(beginPage,engPage)
                    adderList.addAll(tmpList)
                }else{
                    var tmpList = _dataProvince.subList(engPage,_dataProvince.size)
                    adderList.addAll(tmpList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return adderList
        }
    }

}