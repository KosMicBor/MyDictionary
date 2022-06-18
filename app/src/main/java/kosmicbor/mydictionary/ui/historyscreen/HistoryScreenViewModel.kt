package kosmicbor.mydictionary.ui.historyscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.domain.usecases.HistoryScreenUseCase
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState
import kosmicbor.mydictionary.utils.Success
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

class HistoryScreenViewModel(
    private val useCase: HistoryScreenUseCase
) : ViewModel() {

    companion object {
        private const val TEXT_LISTENER_DELAY = 500L
    }

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }
    )

    val textChangedStateFlow = MutableStateFlow("")

    private val _dataToObserve: MutableLiveData<AppState> = MutableLiveData()
    val dataToObserve: LiveData<AppState> = _dataToObserve

    fun getData() {
        val job = viewModelCoroutineScope.launch {
            _dataToObserve.postValue(LoadingState(null))
        }

        job.cancel()

        viewModelCoroutineScope.launch {
            _dataToObserve.postValue(useCase.getDataList())
        }
    }

    fun deleteWordFromDataBase(word: String) {
        val deleteJob = viewModelCoroutineScope.launch {
            useCase.deleteLocalWord(word)
        }

        if (deleteJob.isCompleted) {
            deleteJob.cancel()
        }
    }

    private fun handleError(throwable: Throwable) {
        _dataToObserve.postValue(AppStateError<Throwable>(throwable))
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun searchWordInHistory() {
        viewModelCoroutineScope.launch {

            textChangedStateFlow
                .debounce(TEXT_LISTENER_DELAY)
                .filter { query ->
                    if (query.isEmpty()) {
                        getData()
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest { queryString ->

                    flow {
                        emit(getLocalWordFromDb(queryString))
                    }

                }
                .collect { result ->
                    Timber.tag("@@@").d(result.toString())
                    _dataToObserve.postValue(Success(result))
                }
        }
    }

    private suspend fun getLocalWordFromDb(queryString: String): List<LocalWord> {
        return useCase.getSearchingWord(queryString)
    }

    override fun onCleared() {
        super.onCleared()

        _dataToObserve.value = Success(null)
        viewModelCoroutineScope.cancel()
    }
}