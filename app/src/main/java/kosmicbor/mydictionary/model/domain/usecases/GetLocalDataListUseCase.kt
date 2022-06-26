package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.giftapp.utils.AppState

interface GetLocalDataListUseCase {
    suspend fun getDataList(): AppState
}