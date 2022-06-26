package kosmicbor.mydictionary.model.datasource

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseInterceptor private constructor() : Interceptor {

    companion object {
        private const val ZERO_VAL = 0
        private const val RESPONSE_CODE_DIVIDER = 100
        private const val RESPONSE_CODE_INFO = 1
        private const val RESPONSE_CODE_SUCCESS = 2
        private const val RESPONSE_CODE_REDIRECTION = 3
        private const val RESPONSE_CODE_CLIENT_ERROR = 4
        private const val RESPONSE_CODE_SERVER_ERROR = 5
        val interceptor: BaseInterceptor
            get() = BaseInterceptor()
    }

    private var responseCode: Int = ZERO_VAL

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        responseCode = response.code
        return response
    }

    fun getResponseCode(): ServerResponseStatusCode {
        var statusCode = ServerResponseStatusCode.UNDEFINED_ERROR
        when (responseCode / RESPONSE_CODE_DIVIDER) {
            RESPONSE_CODE_INFO -> statusCode = ServerResponseStatusCode.INFO
            RESPONSE_CODE_SUCCESS -> statusCode = ServerResponseStatusCode.SUCCESS
            RESPONSE_CODE_REDIRECTION -> statusCode = ServerResponseStatusCode.REDIRECTION
            RESPONSE_CODE_CLIENT_ERROR -> statusCode = ServerResponseStatusCode.CLIENT_ERROR
            RESPONSE_CODE_SERVER_ERROR -> statusCode = ServerResponseStatusCode.SERVER_ERROR
        }
        return statusCode
    }

    enum class ServerResponseStatusCode {
        INFO,
        SUCCESS,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNDEFINED_ERROR
    }
}