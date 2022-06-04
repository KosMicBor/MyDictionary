package kosmicbor.mydictionary.model.domain

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.utils.AppState

interface GetTranslationDataUseCase {
    fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ) : Observable<AppState>
}