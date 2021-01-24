package meaw.meow.pjcoviddashboard.utils

data class Resource<out T>(val status: StatusResource,val data: T?,val message:String?) {

    companion object{
        fun <T> success(data: T?): Resource<T> {
            return Resource(StatusResource.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(StatusResource.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(StatusResource.LOADING, data, null)
        }
        fun <T> nothing(): Resource<T> {
            return Resource(StatusResource.NOTHING, null, null)
        }
        fun <T> querystr(data: T?): Resource<T> {
            return Resource(StatusResource.QUERY_STR, data, null)
        }

    }
}