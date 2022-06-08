package kosmicbor.mydictionary.di.modules

import dagger.Module
import dagger.Provides
import kosmicbor.mydictionary.di.qualifies.DictionaryCustomInterceptor
import kosmicbor.mydictionary.model.datasource.BaseInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

@Module
class HttpClientModule {

    @Provides
    fun provideHttpClient(
        @DictionaryCustomInterceptor interceptor: Interceptor,
        httpInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(httpInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {

        Timber.plant(Timber.DebugTree())

        val httpInterceptor = HttpLoggingInterceptor {
            Timber.i(it)
        }.also {
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return httpInterceptor
    }

    @DictionaryCustomInterceptor
    @Provides
    fun provideCustomInterceptor(): Interceptor {
        return BaseInterceptor.interceptor
    }
}
