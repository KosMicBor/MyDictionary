package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

private const val PARAM_WORD_TRANSLATION_DTO_TEXT = "text"
private const val PARAM_WORD_TRANSLATION_DTO_POS = "pos"
private const val PARAM_WORD_TRANSLATION_DTO_SYN = "syn"
private const val PARAM_WORD_TRANSLATION_DTO_MEAN = "mean"
private const val PARAM_WORD_TRANSLATION_DTO_EX = "ex"

data class WordTranslationDto(
    @SerializedName(PARAM_WORD_TRANSLATION_DTO_TEXT)
    val translationText: String?,
    @SerializedName(PARAM_WORD_TRANSLATION_DTO_POS)
    val translationPartOfSpeech: String?,
    @SerializedName(PARAM_WORD_TRANSLATION_DTO_SYN)
    val synonyms: List<SynonymDto>?,
    @SerializedName(PARAM_WORD_TRANSLATION_DTO_MEAN)
    val mean: List<MeanDto>?,
    @SerializedName(PARAM_WORD_TRANSLATION_DTO_EX)
    val examples: List<ExamplesDto>?
)
