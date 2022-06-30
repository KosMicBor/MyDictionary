package kosmicbor.mydictionary.model.data.usecases

import kosmicbor.entities.LocalWord
import kosmicbor.giftapp.utils.Success
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.usecases.HistoryScreenUseCase

class HistoryScreenUseCaseImpl(private val repository: DictionaryRepository) :
    HistoryScreenUseCase {
    override suspend fun getDataList(): kosmicbor.giftapp.utils.AppState {
        return Success(repository.getLocalDataList())
    }

    override suspend fun deleteLocalWord(word: String) {
        repository.deleteWordFromDb(word)
    }

    override suspend fun getSearchingWord(word: String): List<LocalWord> {
        return repository.getSearchWords(word)
    }
}