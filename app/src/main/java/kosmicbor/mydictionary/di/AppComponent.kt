package kosmicbor.mydictionary.di

import dagger.Component
import kosmicbor.mydictionary.di.modules.*
import kosmicbor.mydictionary.di.scopes.DictionaryApplicationScope
import kosmicbor.mydictionary.ui.MainActivity

@DictionaryApplicationScope
@Component(
    modules = [
        HttpClientModule::class,
        DataSourceModule::class,
        RepositoryModule::class,
        UseCasesModule::class,
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
}