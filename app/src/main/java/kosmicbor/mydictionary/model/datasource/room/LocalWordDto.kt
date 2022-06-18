package kosmicbor.mydictionary.model.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = arrayOf("word"), unique = true)])
class LocalWordDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "translation_direction")
    val translationDirection: String,
    @ColumnInfo(name = "date")
    val date: Date
)
