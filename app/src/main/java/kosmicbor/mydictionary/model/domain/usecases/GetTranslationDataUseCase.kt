package kosmicbor.mydictionary.model.domain.usecases

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.utils.AppState
import org.koin.core.component.KoinComponent

interface GetTranslationDataUseCase  {
    suspend fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ) : AppState
}