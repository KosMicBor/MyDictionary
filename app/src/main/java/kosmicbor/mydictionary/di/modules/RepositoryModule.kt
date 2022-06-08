package kosmicbor.mydictionary.di.modules

import dagger.Module
import dagger.Provides
import kosmicbor.mydictionary.di.scopes.DictionaryApplicationScope
import kosmicbor.mydictionary.model.data.repositories.DictionaryRepositoryImpl
import kosmicbor.mydictionary.model.datasource.DataSourceRemote
import kosmicbor.mydictionary.model.domain.DictionaryRepository

@Module(includes = [DataSourceModule::class])
class RepositoryModule {

    @Provides
    @DictionaryApplicationScope
    fun provideDictionaryRepository(dataSource: DataSourceRemote): DictionaryRepository {
        return DictionaryRepositoryImpl(dataSource)
    }
}