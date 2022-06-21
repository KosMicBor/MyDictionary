package kosmicbor.dto

import com.google.gson.annotations.SerializedName

private const val PARAM_SYNONYM_DTO_TEXT = "text"
private const val PARAM_SYNONYM_DTO_POS = "pos"
private const val PARAM_SYNONYM_DTO_GEN = "gen"

data class SynonymDto(
    @SerializedName(PARAM_SYNONYM_DTO_TEXT)
    val synonymText: String?,
    @SerializedName(PARAM_SYNONYM_DTO_POS)
    val synonymPartOfSpeech: String?,
    @SerializedName(PARAM_SYNONYM_DTO_GEN)
    val synonymGender: String?
)
