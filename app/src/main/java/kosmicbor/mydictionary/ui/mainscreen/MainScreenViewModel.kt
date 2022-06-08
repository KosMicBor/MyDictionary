package kosmicbor.mydictionary.ui.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kosmicbor.mydictionary.model.domain.BaseViewModel
import kosmicbor.mydictionary.model.domain.MainScreenUseCase
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState

class MainScreenViewModel(
    private val useCase: MainScreenUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<AppState>() {

    companion object {
        private const val SAVED_LOOKUP_WORD_KEY = "lookupWord"
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override val dataToObserve: MutableLiveData<AppState> = MutableLiveData()

    override fun getData(lookupWord: String, translationDirection: String) {
        compositeDisposable.add(
            useCase.getTranslationData(lookupWord, translationDirection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { dataToObserve.postValue(LoadingState(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {

                dataToObserve.postValue(appState)
            }

            override fun onError(e: Throwable) {
                dataToObserve.postValue(AppStateError<Throwable>(e))
            }

            override fun onComplete() {
            }
        }
    }

    override fun saveLookupWord(word: String) {
        savedStateHandle.set(SAVED_LOOKUP_WORD_KEY, word)
    }

    override fun restoreLookupWord(): String? {
        return savedStateHandle.get<String>(SAVED_LOOKUP_WORD_KEY)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}