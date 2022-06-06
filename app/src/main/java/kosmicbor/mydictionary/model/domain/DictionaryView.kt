package kosmicbor.mydictionary.model.domain

import kosmicbor.mydictionary.utils.AppState

interface DictionaryView {
    fun renderData(appState: AppState)
    fun showProgress()
    fun showStandardViews()
}