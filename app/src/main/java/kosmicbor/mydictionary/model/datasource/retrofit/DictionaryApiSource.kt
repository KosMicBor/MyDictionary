package kosmicbor.mydictionary.model.datasource.retrofit

import kosmicbor.mydictionary.model.datasource.dto.DictionaryDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApiSource {

    @GET("/api/v1/dicservice.json/lookup?")
    suspend fun lookupWordAsync(
        @Query("key") apiKey: String,
        @Query("lang") translateDirection: String,
        @Query("text") searchWord: String,
    ): DictionaryDataDto
}
