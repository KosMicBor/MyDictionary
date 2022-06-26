package kosmicbor.mydictionary.model.domain

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.utils.AppState

abstract class BaseFragment<T : AppState>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    abstract val viewModel: ViewModel

    abstract fun renderData(appState: T)
    abstract fun showProgress()
    abstract fun showStandardViews()
}