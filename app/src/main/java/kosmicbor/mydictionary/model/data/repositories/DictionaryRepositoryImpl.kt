package kosmicbor.mydictionary.model.data.repositories


import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.domain.RemoteDataSource
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.LocalDataSource

class DictionaryRepositoryImpl(
    private val remoteDataSource: RemoteDataSource<List<WordDefinition>>,
    private val localDataSource: LocalDataSource<List<LocalWord>>
) : DictionaryRepository {

    override suspend fun getWordDefinition(
        lookupWord: String,
        translationDirection: String
    ): List<WordDefinition> {
        return remoteDataSource.getRemoteData(lookupWord, translationDirection)
    }

    override suspend fun getLocalDataList(): List<LocalWord> {
        return localDataSource.getLocalData()
    }

    override suspend fun getSearchWords(word: String): List<LocalWord> {
        return localDataSource.getSearchingWords(word)
    }

    override suspend fun saveWordToDb(word: LocalWord) {
        localDataSource.saveDataToLocalSource(word)
    }

    override suspend fun deleteWordFromDb(word: String) {
        localDataSource.deleteDataFromLocalSource(word)
    }
}