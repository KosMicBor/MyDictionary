package kosmicbor.mydictionary.model.domain

import kosmicbor.entities.LocalWord

interface LocalDataSource<T> {
    suspend fun getLocalData(): T
    suspend fun getSearchingWords(word: String): T
    suspend fun deleteDataFromLocalSource(word: String)
    suspend fun saveDataToLocalSource(wordObject: LocalWord)
}