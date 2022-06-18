package kosmicbor.mydictionary.ui.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.domain.BaseMainScreenViewModel
import kosmicbor.mydictionary.model.domain.usecases.MainScreenUseCase
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState
import kosmicbor.mydictionary.utils.Success
import kotlinx.coroutines.launch
import java.util.*

class MainScreenViewModel(
    private val useCase: MainScreenUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMainScreenViewModel<AppState>() {

    companion object {
        private const val SAVED_LOOKUP_WORD_KEY = "lookupWord"
    }

    override val dataToObserve: MutableLiveData<AppState> = MutableLiveData()

    override fun getData(lookupWord: String, translationDirection: String) {
        val job = viewModelCoroutineScope.launch {
            dataToObserve.postValue(LoadingState(null))
        }
        cancelJob(job)

        viewModelCoroutineScope.launch {
            dataToObserve.postValue(useCase.getTranslationData(lookupWord, translationDirection))
        }
    }


    override fun saveLookupWord(word: String) {
        savedStateHandle.set(SAVED_LOOKUP_WORD_KEY, word)
    }

    override fun restoreLookupWord(): String? {
        return savedStateHandle.get<String>(SAVED_LOOKUP_WORD_KEY)
    }

    override fun handleError(throwable: Throwable) {
        dataToObserve.postValue(AppStateError<Throwable>(throwable))
    }

    override fun onCleared() {
        super.onCleared()
        dataToObserve.value = Success(null)
    }

    override suspend fun saveWordToDb(word: String, translationDirection: String) {
        val localWord = LocalWord(word, Date(), translationDirection)
        useCase.saveWOrdToDb(localWord)
    }
}