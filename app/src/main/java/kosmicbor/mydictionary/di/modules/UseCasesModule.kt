package kosmicbor.mydictionary.di.modules

import dagger.Module
import dagger.Provides
import kosmicbor.mydictionary.model.data.usecases.MainScreenUseCaseImpl
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.MainScreenUseCase

@Module
class UseCasesModule {

    @Provides
    fun provideMainScreenUseCase(repo: DictionaryRepository): MainScreenUseCase {
        return MainScreenUseCaseImpl(repo)
    }
}