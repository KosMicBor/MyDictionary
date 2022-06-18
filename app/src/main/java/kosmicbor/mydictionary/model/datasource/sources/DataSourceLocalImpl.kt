package kosmicbor.mydictionary.model.datasource.sources

import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.datasource.room.LocalDictionaryDao
import kosmicbor.mydictionary.model.domain.LocalDataSource
import kosmicbor.mydictionary.utils.convertLocalWordToLocalWordDTO

class DataSourceLocalImpl(private val localProvider: LocalDictionaryDao) :
    LocalDataSource<List<LocalWord>> {

    override suspend fun getLocalData(): List<LocalWord> {
        return localProvider.getSearchWordsHistory().map {
            LocalWord(
                word = it.word,
                date = it.date,
                translationDirection = it.translationDirection
            )
        }
    }

    override suspend fun saveDataToLocalSource(wordObject: LocalWord) {
        val entity = convertLocalWordToLocalWordDTO(wordObject)
        localProvider.insert(entity)
    }

    override suspend fun deleteDataFromLocalSource(word: String) {
        localProvider.delete(word)
    }

    override suspend fun getSearchingWords(word: String): List<LocalWord> {
        return localProvider.getSearchingWords(word).map {
            LocalWord(
                word = it.word,
                date = it.date,
                translationDirection = it.translationDirection
            )
        }
    }
}