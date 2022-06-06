package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

data class MeanDto(
    @SerializedName("text")
    val meanText: String?
)
