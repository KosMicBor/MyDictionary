package kosmicbor.mydictionary.model.datasource.retrofit

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.model.datasource.dto.DictionaryDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApiSource {

    @GET("/api/v1/dicservice.json/lookup?")
    fun lookupWord(
        @Query("key") apiKey: String,
        @Query("lang") translateDirection: String,
        @Query("text") searchWord: String,
    ): Observable<DictionaryDataDto>
}

//https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=API-ключ&lang=en-ru&text=time