package kosmicbor.mydictionary.model.data.usecases

import kosmicbor.entities.LocalWord
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.OnlineRepository
import kosmicbor.mydictionary.model.domain.usecases.MainScreenUseCase
import kotlinx.coroutines.flow.Flow

class MainScreenUseCaseImpl(
    private val repo: DictionaryRepository,
    private val networkRepo: OnlineRepository
) :
    MainScreenUseCase {

    override suspend fun getTranslationData(
        lookupWord: String,
        translationDirection: String
    ): kosmicbor.giftapp.utils.AppState {
        return kosmicbor.giftapp.utils.Success(
            repo.getWordDefinition(
                lookupWord,
                translationDirection
            )
        )
    }

    override suspend fun saveWOrdToDb(word: LocalWord) {
        repo.saveWordToDb(word)
    }

    override fun checkNetworkStatus(): Flow<Boolean> {
        return networkRepo.updateNetworkStatus()
    }

    override fun clear() {
        networkRepo.clear()
    }
}