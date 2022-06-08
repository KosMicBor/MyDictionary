package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

private const val PARAM_MEAN_DTO_TEXT = "text"

data class MeanDto(
    @SerializedName(PARAM_MEAN_DTO_TEXT)
    val meanText: String?
)
