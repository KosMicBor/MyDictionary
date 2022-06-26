package kosmicbor.mydictionary.model.data.datasource.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LocalWordDto::class], version = 1, exportSchema = true)
@TypeConverters(kosmicbor.giftapp.utils.Converters::class)
abstract class LocalDictionaryDataBase: RoomDatabase() {

    abstract fun dictionaryHistoryDao(): LocalDictionaryDao
}