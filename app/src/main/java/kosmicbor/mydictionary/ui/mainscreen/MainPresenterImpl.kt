package kosmicbor.mydictionary.ui.mainscreen

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kosmicbor.mydictionary.model.domain.DictionaryView
import kosmicbor.mydictionary.model.domain.MainPresenter
import kosmicbor.mydictionary.model.domain.MainScreenUseCase
import kosmicbor.mydictionary.ui.MainActivity.Companion.TRANSLATION_DIRECTION
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState

class MainPresenterImpl<V : DictionaryView>(
    private val useCase: MainScreenUseCase,
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : MainPresenter<V> {

    private var currentView: V? = null
    private var currentLookupWord: String? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(
        lookupWord: String,
        translationDirection: String
    ) {
        compositeDisposable.add(
            useCase.getTranslationData(lookupWord, translationDirection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { currentView?.renderData(LoadingState(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {

                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppStateError<Throwable>(e))
            }

            override fun onComplete() {
            }
        }
    }

    override fun saveCurrentLookupWord(word: String) {
        currentLookupWord = word
    }

    override fun restoreState() {
        currentLookupWord?.let {
            getData(it, TRANSLATION_DIRECTION)
        }
    }
}

