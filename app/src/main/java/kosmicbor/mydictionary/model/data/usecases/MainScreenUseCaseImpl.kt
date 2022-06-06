package kosmicbor.mydictionary.model.data.usecases

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.MainScreenUseCase
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.Success

class MainScreenUseCaseImpl(private val repo: DictionaryRepository) :
    MainScreenUseCase {

    override fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ): Observable<AppState> {
        return repo.getWordDefinition(lookupWord, translationDirection).map {
            Success(it)
        }
    }
}