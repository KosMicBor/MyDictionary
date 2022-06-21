package kosmicbor.mydictionary.model.data.datasource.room

import androidx.room.*

@Dao
interface LocalDictionaryDao {

    @Query("SELECT * FROM LocalWordDto ORDER BY date DESC")
    suspend fun getSearchWordsHistory(): List<LocalWordDto>

    @Query("SELECT * FROM LocalWordDto WHERE word = :checkingWord")
    suspend fun getSearchingWords(checkingWord : String) : List<LocalWordDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wordDto: LocalWordDto)

    @Query("DELETE FROM LocalWordDto WHERE word = :word" )
    suspend fun delete(word: String)
}