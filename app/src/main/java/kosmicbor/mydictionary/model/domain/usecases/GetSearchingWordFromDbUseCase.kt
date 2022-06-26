package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.entities.LocalWord

interface GetSearchingWordFromDbUseCase {
    suspend fun getSearchingWord(word: String): List<LocalWord>
}