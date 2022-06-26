package kosmicbor.mydictionary.model.domain.usecases

import org.koin.core.component.KoinComponent

interface MainScreenUseCase : GetTranslationDataUseCase, KoinComponent, SaveWordToLocalDbUseCase, CheckNetworkStatusUseCase