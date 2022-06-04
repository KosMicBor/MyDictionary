package kosmicbor.mydictionary.model.data.repositories


import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.domain.DataSource
import kosmicbor.mydictionary.model.domain.DictionaryRepository

class DictionaryRepositoryImpl(
    private val dataSource: DataSource<List<WordDefinition>>
) : DictionaryRepository {
    override fun getWordDefinition(
        lookupWord: String,
        translationDirection: String
    ): Observable<List<WordDefinition>> {
        return dataSource.getData(lookupWord, translationDirection)
    }
}