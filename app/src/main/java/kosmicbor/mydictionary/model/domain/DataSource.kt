package kosmicbor.mydictionary.model.domain

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.data.WordDefinition

interface DataSource<T> {
    suspend fun getData(lookupWord: String, translationDirection: String): List<WordDefinition>
}