package kosmicbor.mydictionary.model.domain.usecases

import kosmicbor.entities.LocalWord

interface SaveWordToLocalDbUseCase {
    suspend fun saveWOrdToDb(word: LocalWord)
}