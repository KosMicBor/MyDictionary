package kosmicbor.mydictionary.ui.mainscreen

import androidx.lifecycle.SavedStateHandle
import kosmicbor.mydictionary.model.domain.BaseViewModel
import kosmicbor.mydictionary.model.domain.MainScreenUseCase
import kosmicbor.mydictionary.model.domain.ViewModelAssistedFactory
import kosmicbor.mydictionary.utils.AppState
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val useCase: MainScreenUseCase
) : ViewModelAssistedFactory<BaseViewModel<AppState>> {
    override fun create(handle: SavedStateHandle): BaseViewModel<AppState> {
        return MainScreenViewModel(useCase, handle)
    }
}