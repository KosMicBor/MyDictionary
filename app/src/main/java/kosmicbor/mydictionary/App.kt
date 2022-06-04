package kosmicbor.mydictionary

import android.app.Application
import android.content.Context
import kosmicbor.mydictionary.model.data.repositories.DictionaryRepositoryImpl
import kosmicbor.mydictionary.model.data.usecases.MainScreenUseCaseImpl
import kosmicbor.mydictionary.model.datasource.DataSourceRemote
import kosmicbor.mydictionary.model.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.domain.DictionaryView
import kosmicbor.mydictionary.model.domain.MainPresenter
import kosmicbor.mydictionary.ui.mainscreen.MainPresenterImpl

class App : Application() {

    val presenter: MainPresenter<DictionaryView> by lazy {
        MainPresenterImpl(
            MainScreenUseCaseImpl(
                DictionaryRepositoryImpl(
                    DataSourceRemote(
                        RetrofitImpl()
                    )
                )
            )
        )
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }