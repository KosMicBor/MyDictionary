package kosmicbor.mydictionary.model.data.usecases

import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.usecases.MainScreenUseCase
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.Success

class MainScreenUseCaseImpl(private val repo: DictionaryRepository) :
    MainScreenUseCase {

    override suspend fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ): AppState {
        return Success(repo.getWordDefinition(lookupWord, translationDirection))
    }

    override suspend fun saveWOrdToDb(word: LocalWord) {
        repo.saveWordToDb(word)
    }
}