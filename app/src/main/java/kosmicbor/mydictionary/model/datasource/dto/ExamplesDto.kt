package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

private const val PARAM_EXAMPLES_DTO_TEXT = "text"
private const val PARAM_EXAMPLES_DTO_TR = "tr"

data class ExamplesDto(
    @SerializedName(PARAM_EXAMPLES_DTO_TEXT)
    val exampleText: String,
    @SerializedName(PARAM_EXAMPLES_DTO_TR)
    val exampleTranslation: List<ExampleTranslationDto>,
)