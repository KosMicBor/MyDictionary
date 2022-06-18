package kosmicbor.mydictionary.model.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kosmicbor.mydictionary.utils.Converters

@Database(entities = [LocalWordDto::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class LocalDictionaryDataBase: RoomDatabase() {

    abstract fun dictionaryHistoryDao(): LocalDictionaryDao
}