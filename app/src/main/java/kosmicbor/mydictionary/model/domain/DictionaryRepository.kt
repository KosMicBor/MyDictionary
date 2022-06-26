package kosmicbor.mydictionary.model.domain

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.data.WordDefinition

interface DictionaryRepository {
    suspend fun getWordDefinition(
        lookupWord: String,
        translationDirection: String
    ): List<WordDefinition>
}