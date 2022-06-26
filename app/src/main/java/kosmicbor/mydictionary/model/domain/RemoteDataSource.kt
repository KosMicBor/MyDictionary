package kosmicbor.mydictionary.model.domain

interface RemoteDataSource<T> {
    suspend fun getRemoteData(lookupWord: String, translationDirection: String): T
}