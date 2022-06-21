package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.giftapp.utils.AppState

interface GetTranslationDataUseCase {
    suspend fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ): AppState
}