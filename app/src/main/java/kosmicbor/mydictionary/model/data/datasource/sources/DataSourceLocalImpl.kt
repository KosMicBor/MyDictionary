package kosmicbor.mydictionary.model.data.datasource.sources

import kosmicbor.entities.LocalWord
import kosmicbor.giftapp.utils.ZERO_VAL
import kosmicbor.mydictionary.model.data.datasource.room.LocalDictionaryDao
import kosmicbor.mydictionary.model.data.datasource.room.LocalWordDto
import kosmicbor.mydictionary.model.domain.LocalDataSource

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

    private fun convertLocalWordToLocalWordDTO(wordObject: LocalWord): LocalWordDto {
        return LocalWordDto(
            id = ZERO_VAL,
            word = wordObject.word,
            translationDirection = wordObject.translationDirection,
            date = wordObject.date
        )

    }
}