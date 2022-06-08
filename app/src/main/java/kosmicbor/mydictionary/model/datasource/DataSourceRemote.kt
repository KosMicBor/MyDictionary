package kosmicbor.mydictionary.model.datasource

import io.reactivex.rxjava3.core.Observable
import kosmicbor.mydictionary.di.qualifies.DictionaryApiKey
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.datasource.retrofit.RetrofitImpl
import kosmicbor.mydictionary.model.domain.DataSource
import kosmicbor.mydictionary.utils.convertWordDefinitionDtoToWordDefinition
import javax.inject.Inject


class DataSourceRemote @Inject constructor(
    private val remoteProvider: RetrofitImpl,
    @DictionaryApiKey private val apiKey: String
) :
    DataSource<List<WordDefinition>> {
    override fun getData(
        lookupWord: String,
        translationDirection: String
    ): Observable<List<WordDefinition>> {
        return remoteProvider.getData(lookupWord, apiKey, translationDirection)
            .map {
                convertWordDefinitionDtoToWordDefinition(it)
            }
    }
}