package kosmicbor.giftapp.utils

sealed class AppState

data class Success<T>(val value: T) : AppState()
data class AppStateError<T>(val error: Throwable) : AppState()
data class LoadingState(val percent: Int?): AppState()