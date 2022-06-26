package kosmicbor.mydictionary.model.data.usecases

import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.usecases.WordDescriptionScreenUseCase
import kosmicbor.giftapp.utils.AppState
import kosmicbor.giftapp.utils.Success

class WordDescriptionScreenUseCaseImpl(private val repository: DictionaryRepository) :
    WordDescriptionScreenUseCase {
    override suspend fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ): AppState {
        return Success(
            repository.getWordDefinition(
                lookupWord,
                translationDirection
            )
        )
    }
}