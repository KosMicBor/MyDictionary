package kosmicbor.mydictionary.model.data.datasource.retrofit

import kosmicbor.dto.WordDefinitionDto

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