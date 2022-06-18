package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.mydictionary.utils.AppState

interface GetLocalDataListUseCase {
    suspend fun getDataList(): AppState
}