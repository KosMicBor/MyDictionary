package kosmicbor.mydictionary

import android.app.Application
import android.content.Context
import kosmicbor.mydictionary.di.AppComponent
import kosmicbor.mydictionary.di.DaggerAppComponent
import kosmicbor.mydictionary.di.modules.*

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .dataSourceModule(DataSourceModule())
            .httpClientModule(HttpClientModule())
            .repositoryModule(RepositoryModule())
            .useCasesModule(UseCasesModule())
            .build()
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }