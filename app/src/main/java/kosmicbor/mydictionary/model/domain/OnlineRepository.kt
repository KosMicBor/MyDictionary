package kosmicbor.mydictionary.model.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface OnlineRepository {
    val statusToObserve: StateFlow<Boolean>
    fun updateNetworkStatus() : Flow<Boolean>
    fun clear()
}