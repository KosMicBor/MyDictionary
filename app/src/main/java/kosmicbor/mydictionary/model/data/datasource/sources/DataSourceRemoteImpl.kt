package kosmicbor.mydictionary.model.data.datasource.sources

import kosmicbor.entities.WordDefinition
import kosmicbor.mydictionary.model.data.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.domain.RemoteDataSource


class DataSourceRemoteImpl(
    private val remoteProvider: RetrofitImpl,
    private val apiKey: String
) : RemoteDataSource<List<WordDefinition>> {

    override suspend fun getRemoteData(
        lookupWord: String,
        translationDirection: String
    ): List<WordDefinition> {
        return kosmicbor.giftapp.utils.convertWordDefinitionDtoToWordDefinition(
            remoteProvider.getData(
                lookupWord,
                apiKey,
                translationDirection
            )
        )
    }
}