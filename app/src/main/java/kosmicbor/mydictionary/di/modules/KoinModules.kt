package kosmicbor.mydictionary.di.modules

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import kosmicbor.mydictionary.BuildConfig
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.data.repositories.DictionaryRepositoryImpl
import kosmicbor.mydictionary.model.data.usecases.MainScreenUseCaseImpl
import kosmicbor.mydictionary.model.datasource.BaseInterceptor
import kosmicbor.mydictionary.model.datasource.DataSourceRemote
import kosmicbor.mydictionary.model.datasource.retrofit.DictionaryApiSource
import kosmicbor.mydictionary.model.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.domain.DataSource
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.MainScreenUseCase
import kosmicbor.mydictionary.ui.mainscreen.MainScreenViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

private const val BASE_URL = "https://dictionary.yandex.net/"
private const val BASE_URL_NAME = "dictionary_api_url"
private const val API_KEY_NAME = "api_key"
private const val REMOTE_PROVIDER_NAME = "api_source"
private const val REMOTE_DATA_SOURCE_NAME = "remote_data_source"
private const val DICTIONARY_API_SERVICE_NAME = "dictionary_api"
private const val RETROFIT_NAME = "dictionary_retrofit"
private const val GSON_CONVERTER_FACTORY_NAME = "gson_converter_factory"
private const val RX_JAVA_CALL_ADAPTER_NAME = "rx_java_3_call_adapter_factory"
private const val OK_HTTP_CLIENT_NAME = "ok_http_client"
private const val HTTP_LOGGING_INTERCEPTOR_NAME = "http_logging_interceptor"
private const val CUSTOM_LOGGING_INTERCEPTOR_NAME = "custom_logging_interceptor"
private const val DICTIONARY_REPOSITORY_NAME = "dictionary_repository"
private const val DICTIONARY_MAIN_SCREEN_USECASE_NAME = "main_screen_usecase"

val dataSourceModule = module {

    single(named(BASE_URL_NAME)) { BASE_URL }

    factory<Converter.Factory>(named(GSON_CONVERTER_FACTORY_NAME)) {
        GsonConverterFactory.create()
    }

    factory<CallAdapter.Factory>(named(RX_JAVA_CALL_ADAPTER_NAME)) {
        RxJava3CallAdapterFactory.create()
    }

    single<Retrofit>(named(RETROFIT_NAME)) {
        Retrofit.Builder()
            .baseUrl(get<String>(qualifier = named(BASE_URL_NAME)))
            .addConverterFactory(get(qualifier = named(GSON_CONVERTER_FACTORY_NAME)))
            .addCallAdapterFactory(get(qualifier = named(RX_JAVA_CALL_ADAPTER_NAME)))
            .client(get(qualifier = named(OK_HTTP_CLIENT_NAME)))
            .build()
    }

    single(named(DICTIONARY_API_SERVICE_NAME)) {
        get<Retrofit>(qualifier = named(RETROFIT_NAME)).create(DictionaryApiSource::class.java)
    }

    single(named(REMOTE_PROVIDER_NAME)) {
        RetrofitImpl(
            apiService = get(
                named(
                    DICTIONARY_API_SERVICE_NAME
                )
            )
        )
    }

    single(named(API_KEY_NAME)) { BuildConfig.API_KEY }

    single<DataSource<List<WordDefinition>>>(named(REMOTE_DATA_SOURCE_NAME)) {
        DataSourceRemote(
            remoteProvider = get(named(REMOTE_PROVIDER_NAME)),
            apiKey = get(named(API_KEY_NAME))
        )
    }
}

val httpClientModule = module {

    single(named(OK_HTTP_CLIENT_NAME)) {
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor = get(named(CUSTOM_LOGGING_INTERCEPTOR_NAME)))
            .addInterceptor(
                interceptor = get<HttpLoggingInterceptor>(
                    named(
                        HTTP_LOGGING_INTERCEPTOR_NAME
                    )
                )
            )
            .build()
    }

    factory(named(HTTP_LOGGING_INTERCEPTOR_NAME)) {
        Timber.plant(Timber.DebugTree())

        val httpInterceptor = HttpLoggingInterceptor {
            Timber.i(it)
        }.also {
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return@factory httpInterceptor
    }

    factory<Interceptor>(qualifier = named(CUSTOM_LOGGING_INTERCEPTOR_NAME)) {
        BaseInterceptor.interceptor
    }
}

val repositoryModule = module {
    single<DictionaryRepository>(qualifier = named(DICTIONARY_REPOSITORY_NAME)) {
        DictionaryRepositoryImpl(dataSource = get(named(REMOTE_DATA_SOURCE_NAME)))
    }
}

val useCasesModule = module {
    factory<MainScreenUseCase>(qualifier = named(DICTIONARY_MAIN_SCREEN_USECASE_NAME)) {
        MainScreenUseCaseImpl(repo = get(named(DICTIONARY_REPOSITORY_NAME)))
    }
}

val viewModelsModule = module {
    viewModel { params ->
        MainScreenViewModel(
            useCase = get(named(DICTIONARY_MAIN_SCREEN_USECASE_NAME)),
            savedStateHandle = params.get()
        )
    }
}