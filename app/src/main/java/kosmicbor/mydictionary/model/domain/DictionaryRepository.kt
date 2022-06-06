package kosmicbor.mydictionary.model.domain

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.data.WordDefinition

interface DictionaryRepository {
    fun getWordDefinition(
        lookupWord: String,
        translationDirection: String
    ): Observable<List<WordDefinition>>
}