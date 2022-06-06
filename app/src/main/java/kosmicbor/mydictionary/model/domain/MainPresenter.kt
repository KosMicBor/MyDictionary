package kosmicbor.mydictionary.model.domain

interface MainPresenter<V : DictionaryView> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(lookupWord: String, translationDirection: String)

    fun saveCurrentLookupWord(word: String)

    fun restoreState()
}