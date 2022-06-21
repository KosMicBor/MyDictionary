package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.mydictionary.utils.AppState

interface GetTranslationDataUseCase {
    suspend fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ): AppState
}