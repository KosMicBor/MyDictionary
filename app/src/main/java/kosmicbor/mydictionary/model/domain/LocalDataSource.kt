package kosmicbor.mydictionary.model.domain

import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.datasource.room.LocalWordDto

interface LocalDataSource<T> {
    suspend fun getLocalData(): T
    suspend fun getSearchingWords(word: String): T
    suspend fun deleteDataFromLocalSource(word: String)
    suspend fun saveDataToLocalSource(wordObject: LocalWord)
}