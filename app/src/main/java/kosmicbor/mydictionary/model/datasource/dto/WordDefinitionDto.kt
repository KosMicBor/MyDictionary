package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

private const val PARAM_WORD_DEFINITION_DTO_TEXT = "text"
private const val PARAM_WORD_DEFINITION_DTO_POS = "pos"
private const val PARAM_WORD_DEFINITION_DTO_TS = "ts"
private const val PARAM_WORD_DEFINITION_DTO_TR = "tr"

data class WordDefinitionDto(
    @SerializedName(PARAM_WORD_DEFINITION_DTO_TEXT)
    val text: String,
    @SerializedName(PARAM_WORD_DEFINITION_DTO_POS)
    val partOfSpeech: String?,
    @SerializedName(PARAM_WORD_DEFINITION_DTO_TS)
    val transcription: String?,
    @SerializedName(PARAM_WORD_DEFINITION_DTO_TR)
    val translationArray: List<WordTranslationDto>?
)