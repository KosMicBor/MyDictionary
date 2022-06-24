package kosmicbor.mydictionary.model.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kosmicbor.mydictionary.model.domain.OnlineRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnlineRepositoryImpl(context: Context) : OnlineRepository {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val availableNetworksList = mutableListOf<Network>()

    private val networkRequest: NetworkRequest = NetworkRequest.Builder().build()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            availableNetworksList.remove(network)
            updateStatus(availableNetworksList.isNotEmpty())
        }

        override fun onAvailable(network: Network) {
            availableNetworksList.add(network)
            updateStatus(availableNetworksList.isNotEmpty())
        }
    }

    override val statusToObserve = MutableStateFlow(false)

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private fun updateStatus(status: Boolean) {
        scope.launch {
            statusToObserve.emit(status)
        }
    }

    override fun updateNetworkStatus(): StateFlow<Boolean> {

        connectivityManager.registerNetworkCallback(
            networkRequest,
            networkCallback
        )

        return statusToObserve.asStateFlow()
    }

    override fun clear() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        scope.cancel()
    }
}