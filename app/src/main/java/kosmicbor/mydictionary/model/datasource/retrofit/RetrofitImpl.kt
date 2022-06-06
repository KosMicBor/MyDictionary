package kosmicbor.mydictionary.model.datasource.retrofit

import android.util.Log
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.datasource.BaseInterceptor
import kosmicbor.mydictionary.model.datasource.dto.WordDefinitionDto
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {

    companion object {
        private const val BASE_URL = "https://dictionary.yandex.net/"
    }

    fun getData(
        lookupWord: String,
        apiKey: String,
        translationDirection: String
    ): Observable<List<WordDefinitionDto>> {

        return getService(BaseInterceptor.interceptor).lookupWord(
            apiKey,
            translationDirection,
            lookupWord
        )
            .map {
                Log.d("@@@", "getDataDto: ${it.definition}")
                it.definition
            }
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    private fun createRetrofit(interceptor: Interceptor): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(createOkHttpClient(interceptor))
            .build()
    }

    private fun getService(interceptor: Interceptor): DictionaryApiSource {
        return createRetrofit(interceptor).create(DictionaryApiSource::class.java)
    }
}