package kosmicbor.mydictionary.model.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kosmicbor.mydictionary.utils.AppState

abstract class BaseViewModel<T : AppState> : ViewModel() {
    abstract val dataToObserve: LiveData<T>
    abstract fun getData(lookupWord: String, translationDirection: String)
    abstract fun saveLookupWord(word: String)
    abstract fun restoreLookupWord(): String?
}