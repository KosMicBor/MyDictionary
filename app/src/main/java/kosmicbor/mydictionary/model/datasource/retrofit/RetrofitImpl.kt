package kosmicbor.mydictionary.model.datasource.retrofit

import kosmicbor.mydictionary.model.datasource.dto.WordDefinitionDto

class RetrofitImpl(private val apiService: DictionaryApiSource) {

    suspend fun getData(
        lookupWord: String,
        apiKey: String,
        translationDirection: String
    ): List<WordDefinitionDto> {

        return apiService.lookupWordAsync(
            apiKey,
            translationDirection,
            lookupWord
        ).definition
    }
}