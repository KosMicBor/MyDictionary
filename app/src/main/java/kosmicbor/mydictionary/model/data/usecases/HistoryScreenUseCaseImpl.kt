package kosmicbor.mydictionary.model.data.usecases

import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kosmicbor.mydictionary.model.domain.usecases.HistoryScreenUseCase
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.Success

class HistoryScreenUseCaseImpl(private val repository: DictionaryRepository) :
    HistoryScreenUseCase {
    override suspend fun getDataList(): AppState {
        return Success(repository.getLocalDataList())
    }

    override suspend fun deleteLocalWord(word: String) {
        repository.deleteWordFromDb(word)
    }

    override suspend fun getSearchingWord(word: String): List<LocalWord> {
        return repository.getSearchWords(word)
    }
}