package kosmicbor.mydictionary.model.datasource.sources

import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.domain.RemoteDataSource
import kosmicbor.mydictionary.utils.convertWordDefinitionDtoToWordDefinition


class DataSourceRemoteImpl(
    private val remoteProvider: RetrofitImpl,
    private val apiKey: String
) : RemoteDataSource<List<WordDefinition>> {

    override suspend fun getRemoteData(
        lookupWord: String,
        translationDirection: String
    ): List<WordDefinition> {
        return convertWordDefinitionDtoToWordDefinition(
            remoteProvider.getData(
                lookupWord,
                apiKey,
                translationDirection
            )
        )
    }
}