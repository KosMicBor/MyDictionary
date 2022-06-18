package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.mydictionary.model.data.LocalWord

interface SaveWordToLocalDbUseCase {
    suspend fun saveWOrdToDb(word: LocalWord)
}