package kosmicbor.mydictionary.model.domain.usecases

interface DeleteLocalWordUseCase {
    suspend fun deleteLocalWord(word: String)
}