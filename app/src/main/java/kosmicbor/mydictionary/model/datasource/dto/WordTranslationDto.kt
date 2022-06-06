package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

data class WordTranslationDto(
    @SerializedName("text")
    val translationText: String?,
    @SerializedName("pos")
    val translationPartOfSpeech: String?,
    @SerializedName("syn")
    val synonyms: List<SynonymDto>?,
    @SerializedName("mean")
    val mean: List<MeanDto>?,
    @SerializedName("ex")
    val examples: List<ExamplesDto>?
)
