package kosmicbor.mydictionary.di.modules

import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import kosmicbor.mydictionary.BuildConfig
import kosmicbor.mydictionary.di.qualifies.DictionaryApiKey
import kosmicbor.mydictionary.di.qualifies.RetrofitBaseUrl
import kosmicbor.mydictionary.di.scopes.DictionaryApplicationScope
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.datasource.DataSourceRemote
import kosmicbor.mydictionary.model.datasource.retrofit.DictionaryApiSource
import kosmicbor.mydictionary.model.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.domain.DataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [HttpClientModule::class])
class DataSourceModule {

    companion object {
        private const val BASE_URL = "https://dictionary.yandex.net/"
    }

    @DictionaryApiKey
    @Provides
    fun provideApiKey(): String {
        return BuildConfig.API_KEY
    }

    @Provides
    @DictionaryApplicationScope
    fun provideRemoteDataSource(
        remoteProvider: RetrofitImpl,
        @DictionaryApiKey apiKey: String
    ): DataSource<List<WordDefinition>> {
        return DataSourceRemote(remoteProvider, apiKey)
    }

    @Provides
    @DictionaryApplicationScope
    fun provideApiService(retrofit: Retrofit): DictionaryApiSource {
        return retrofit.create(DictionaryApiSource::class.java)
    }

    @Provides
    @DictionaryApplicationScope
    fun provideRemoteProvider(apiService: DictionaryApiSource): RetrofitImpl {
        return RetrofitImpl(apiService)
    }

    @Provides
    @DictionaryApplicationScope
    fun provideRetrofit(
        @RetrofitBaseUrl baseUrl: String,
        callAdapterFactory: RxJava3CallAdapterFactory,
        gsonConverterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(client)
            .build()

    }

    @RetrofitBaseUrl
    @Provides
    fun provideRetrofitBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }
}