package kosmicbor.mydictionary.model.data.datasource.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

private const val COLUMN_NAME_WORD = "word"
private const val COLUMN_NAME_TRANSITION_DIRECTION = "translation_direction"
private const val COLUMN_NAME_DATE = "date"

@Entity(indices = [Index(value = arrayOf(COLUMN_NAME_WORD), unique = true)])
class LocalWordDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = COLUMN_NAME_WORD)
    val word: String,
    @ColumnInfo(name = COLUMN_NAME_TRANSITION_DIRECTION)
    val translationDirection: String,
    @ColumnInfo(name = COLUMN_NAME_DATE)
    val date: Date
)
