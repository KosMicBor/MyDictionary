package kosmicbor.mydictionary

import android.app.Application
import android.content.Context
import kosmicbor.mydictionary.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(
                dataSourceModule,
                httpClientModule,
                repositoryModule,
                useCasesModule,
                viewModelsModule
            )
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }