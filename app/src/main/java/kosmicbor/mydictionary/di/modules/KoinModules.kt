package kosmicbor.mydictionary.di.modules

import androidx.room.Room
import kosmicbor.entities.LocalWord
import kosmicbor.entities.WordDefinition
import kosmicbor.mydictionary.BuildConfig
import kosmicbor.mydictionary.model.data.datasource.BaseInterceptor
import kosmicbor.mydictionary.model.data.datasource.retrofit.DictionaryApiSource
import kosmicbor.mydictionary.model.data.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.data.datasource.room.LocalDictionaryDataBase
import kosmicbor.mydictionary.model.data.datasource.sources.DataSourceLocalImpl
import kosmicbor.mydictionary.model.data.datasource.sources.DataSourceRemoteImpl
import kosmicbor.mydictionary.model.data.repositories.DictionaryRepositoryImpl
import kosmicbor.mydictionary.model.data.repositories.OnlineRepositoryImpl
import kosmicbor.mydictionary.model.data.usecases.HistoryScreenUseCaseImpl
import kosmicbor.mydictionary.model.data.usecases.MainScreenUseCaseImpl
import kosmicbor.mydictionary.model.data.usecases.WordDescriptionScreenUseCaseImpl
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.LocalDataSource
import kosmicbor.mydictionary.model.domain.OnlineRepository
import kosmicbor.mydictionary.model.domain.RemoteDataSource
import kosmicbor.mydictionary.model.domain.usecases.HistoryScreenUseCase
import kosmicbor.mydictionary.model.domain.usecases.MainScreenUseCase
import kosmicbor.mydictionary.model.domain.usecases.WordDescriptionScreenUseCase
import kosmicbor.mydictionary.ui.historyscreen.HistoryScreenFragment
import kosmicbor.mydictionary.ui.historyscreen.HistoryScreenViewModel
import kosmicbor.mydictionary.ui.mainscreen.MainScreenFragment
import kosmicbor.mydictionary.ui.mainscreen.MainScreenViewModel
import kosmicbor.mydictionary.ui.worddescriptionscreen.WordDescriptionScreenFragment
import kosmicbor.mydictionary.ui.worddescriptionscreen.WordDescriptionScreenViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
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
private const val OK_HTTP_CLIENT_NAME = "ok_http_client"
private const val HTTP_LOGGING_INTERCEPTOR_NAME = "http_logging_interceptor"
private const val CUSTOM_LOGGING_INTERCEPTOR_NAME = "custom_logging_interceptor"
private const val DICTIONARY_REPOSITORY_NAME = "dictionary_repository"
private const val ONLINE_REPOSITORY_NAME = "online_repository"
private const val DICTIONARY_MAIN_SCREEN_USECASE_NAME = "main_screen_usecase"
private const val DICTIONARY_HISTORY_SCREEN_USECASE_NAME = "history_screen_usecase"
private const val DICTIONARY_WORD_DESCRIPTION_SCREEN_USECASE_NAME =
    "word_description_screen_usecase"
private const val LOCAL_DATABASE_NAME = "local_data_base"
private const val DICTIONARY_HISTORY_DAO_NAME = "database_instance"
private const val DATABASE_NAME = "DictionaryHistoryDB"
private const val LOCAL_DATA_SOURCE_NAME = "local_data_source"

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

val dataSourceModule = module {

    //Retrofit
    single(named(BASE_URL_NAME)) { BASE_URL }

    factory<Converter.Factory>(named(GSON_CONVERTER_FACTORY_NAME)) {
        GsonConverterFactory.create()
    }

    single<Retrofit>(named(RETROFIT_NAME)) {
        Retrofit.Builder()
            .baseUrl(get<String>(qualifier = named(BASE_URL_NAME)))
            .addConverterFactory(get(qualifier = named(GSON_CONVERTER_FACTORY_NAME)))
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

    single<RemoteDataSource<List<WordDefinition>>>(named(REMOTE_DATA_SOURCE_NAME)) {
        DataSourceRemoteImpl(
            remoteProvider = get(named(REMOTE_PROVIDER_NAME)),
            apiKey = get(named(API_KEY_NAME))
        )
    }

    //Room
    factory(named(DATABASE_NAME)) { "DictionaryDB.db" }

    single(named(LOCAL_DATABASE_NAME)) {
        Room.databaseBuilder(
            androidContext(),
            LocalDictionaryDataBase::class.java,
            get(named(DATABASE_NAME))
        ).build()
    }

    single(named(DICTIONARY_HISTORY_DAO_NAME)) {
        val dataBase = get<LocalDictionaryDataBase>(named(LOCAL_DATABASE_NAME))

        return@single dataBase.dictionaryHistoryDao()
    }

    single<LocalDataSource<List<LocalWord>>>(named(LOCAL_DATA_SOURCE_NAME)) {
        DataSourceLocalImpl(localProvider = get(named(DICTIONARY_HISTORY_DAO_NAME)))
    }
}

val repositoryModule = module {
    single<DictionaryRepository>(qualifier = named(DICTIONARY_REPOSITORY_NAME)) {
        DictionaryRepositoryImpl(
            remoteDataSource = get(named(REMOTE_DATA_SOURCE_NAME)),
            localDataSource = get(named(LOCAL_DATA_SOURCE_NAME))
        )
    }

    single<OnlineRepository>(named(ONLINE_REPOSITORY_NAME)) { OnlineRepositoryImpl(androidContext()) }
}

val mainScreenModule = module {

    scope<MainScreenFragment> {
        scoped<MainScreenUseCase>(qualifier = named(DICTIONARY_MAIN_SCREEN_USECASE_NAME)) {
            MainScreenUseCaseImpl(
                repo = get(named(DICTIONARY_REPOSITORY_NAME)),
                networkRepo = get(named(ONLINE_REPOSITORY_NAME))
            )
        }

        viewModel { params ->
            MainScreenViewModel(
                useCase = get(named(DICTIONARY_MAIN_SCREEN_USECASE_NAME)),
                savedStateHandle = params.get()
            )
        }
    }
}

val historyScreenModule = module {

    scope<HistoryScreenFragment> {

        scoped<HistoryScreenUseCase>(qualifier = named(DICTIONARY_HISTORY_SCREEN_USECASE_NAME)) {
            HistoryScreenUseCaseImpl(repository = get(named(DICTIONARY_REPOSITORY_NAME)))
        }

        viewModel {
            HistoryScreenViewModel(useCase = get(named(DICTIONARY_HISTORY_SCREEN_USECASE_NAME)))
        }
    }
}

val wordDescriptionScreenViewModel = module {

    scope<WordDescriptionScreenFragment> {

        scoped<WordDescriptionScreenUseCase>(
            qualifier = named(
                DICTIONARY_WORD_DESCRIPTION_SCREEN_USECASE_NAME
            )
        ) {
            WordDescriptionScreenUseCaseImpl(repository = get(named(DICTIONARY_REPOSITORY_NAME)))
        }

        viewModel {
            WordDescriptionScreenViewModel(
                useCase = get(
                    named(
                        DICTIONARY_WORD_DESCRIPTION_SCREEN_USECASE_NAME
                    )
                )
            )
        }
    }


}