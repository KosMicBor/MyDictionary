package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

data class WordDefinitionDto(
    @SerializedName("text")
    val text: String,
    @SerializedName("pos")
    val partOfSpeech: String?,
    @SerializedName("ts")
    val transcription: String?,
    @SerializedName("tr")
    val translationArray: List<WordTranslationDto>?
)