package kosmicbor.dto

import com.google.gson.annotations.SerializedName

private const val PARAM_EXAMPLE_TRANSLATION_DTO_TEXT = "text"

data class ExampleTranslationDto(
    @SerializedName(PARAM_EXAMPLE_TRANSLATION_DTO_TEXT)
    val exampleTranslationText: String?
)
