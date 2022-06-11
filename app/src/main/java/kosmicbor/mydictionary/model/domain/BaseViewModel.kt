package kosmicbor.mydictionary.model.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kosmicbor.mydictionary.utils.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState> : ViewModel() {

    protected open val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected open fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract val dataToObserve: LiveData<T>
    abstract fun getData(lookupWord: String, translationDirection: String)
    abstract fun saveLookupWord(word: String)
    abstract fun restoreLookupWord(): String?
    abstract fun handleError(throwable: Throwable)
}