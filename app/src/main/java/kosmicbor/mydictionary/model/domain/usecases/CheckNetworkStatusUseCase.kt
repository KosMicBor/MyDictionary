package kosmicbor.mydictionary.model.domain.usecases

import kotlinx.coroutines.flow.Flow

interface CheckNetworkStatusUseCase {
    fun checkNetworkStatus(): Flow<Boolean>
    fun clear()
}