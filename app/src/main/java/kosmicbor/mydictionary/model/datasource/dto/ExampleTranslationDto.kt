package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

data class ExampleTranslationDto(
    @SerializedName("text")
    val exampleTranslationText: String?
)
