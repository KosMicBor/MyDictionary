package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

data class DictionaryDataDto(
    @SerializedName("def")
    val definition: List<WordDefinitionDto>
)
