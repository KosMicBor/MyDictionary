package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.mydictionary.model.data.LocalWord

interface GetSearchingWordFromDbUseCase {
    suspend fun getSearchingWord(word: String): List<LocalWord>
}