package kosmicbor.mydictionary.model.data.repositories


import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.domain.DataSource
import kosmicbor.mydictionary.model.domain.DictionaryRepository

class DictionaryRepositoryImpl(
    private val dataSource: DataSource<List<WordDefinition>>
) : DictionaryRepository {

    override suspend fun getWordDefinition(
        lookupWord: String,
        translationDirection: String
    ): List<WordDefinition> {
        return dataSource.getData(lookupWord, translationDirection)
    }
}