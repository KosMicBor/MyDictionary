package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

data class SynonymDto(
    @SerializedName("text")
    val synonymText: String?,
    @SerializedName("pos")
    val synonymPartOfSpeech: String?,
    @SerializedName("gen")
    val synonymGender: String?
)
