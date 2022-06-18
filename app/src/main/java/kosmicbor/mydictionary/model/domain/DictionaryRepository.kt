package kosmicbor.mydictionary.model.domain

import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.data.WordDefinition

interface DictionaryRepository {
    suspend fun getWordDefinition(
        lookupWord: String,
        translationDirection: String
    ): List<WordDefinition>

    suspend fun getLocalDataList(): List<LocalWord>

    suspend fun getSearchWords(word: String): List<LocalWord>

    suspend fun saveWordToDb(word: LocalWord)

    suspend fun deleteWordFromDb(word: String)
}