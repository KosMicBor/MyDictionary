package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

data class ExamplesDto (
    @SerializedName("text")
    val exampleText: String,
    @SerializedName("tr")
    val exampleTranslation: List<ExampleTranslationDto>,
)
