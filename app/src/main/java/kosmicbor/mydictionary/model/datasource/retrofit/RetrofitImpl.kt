package kosmicbor.mydictionary.model.datasource.retrofit

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.datasource.dto.WordDefinitionDto

class RetrofitImpl(private val apiService: DictionaryApiSource) {

    fun getData(
        lookupWord: String,
        apiKey: String,
        translationDirection: String
    ): Observable<List<WordDefinitionDto>> {

        return apiService.lookupWord(
            apiKey,
            translationDirection,
            lookupWord
        )
            .map {
                it.definition
            }
    }
}