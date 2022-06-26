package kosmicbor.mydictionary.ui.worddescriptionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.mydictionary.model.domain.usecases.WordDescriptionScreenUseCase
import kosmicbor.giftapp.utils.AppState
import kosmicbor.giftapp.utils.AppStateError
import kosmicbor.giftapp.utils.LoadingState
import kotlinx.coroutines.*

class WordDescriptionScreenViewModel(
    private val useCase: WordDescriptionScreenUseCase
) : ViewModel() {

    private val _dataToObserve: MutableLiveData<AppState> = MutableLiveData()
    val dataToObserve: LiveData<AppState> = _dataToObserve
    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    suspend fun getData(word: String, transitionDirection: String) {
        val job = viewModelCoroutineScope.launch {
            _dataToObserve.postValue(LoadingState(null))
        }

        job.cancel()

        viewModelCoroutineScope.launch {
            _dataToObserve.postValue(useCase.getTranslationData(word, transitionDirection))
        }
    }

    private fun handleError(throwable: Throwable) {
        _dataToObserve.postValue(AppStateError<Throwable>(throwable))
    }

    override fun onCleared() {
        super.onCleared()

        viewModelCoroutineScope.cancel()
    }
}