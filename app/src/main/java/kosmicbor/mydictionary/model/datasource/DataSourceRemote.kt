package kosmicbor.mydictionary.model.datasource

import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.domain.DataSource
import kosmicbor.mydictionary.utils.convertWordDefinitionDtoToWordDefinition


class DataSourceRemote(
    private val remoteProvider: RetrofitImpl,
    private val apiKey: String
) : DataSource<List<WordDefinition>> {

    override suspend fun getData(
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