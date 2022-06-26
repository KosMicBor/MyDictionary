package kosmicbor.mydictionary.model.datasource.dto

import com.google.gson.annotations.SerializedName

private const val PARAM_DICTIONARY_DATA_DTO_DEF = "def"

data class DictionaryDataDto(

    @SerializedName(value = PARAM_DICTIONARY_DATA_DTO_DEF)
    val definition: List<WordDefinitionDto>


)
