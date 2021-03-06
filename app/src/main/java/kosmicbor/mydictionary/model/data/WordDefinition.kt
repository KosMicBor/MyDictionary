package kosmicbor.mydictionary.model.data

data class WordDefinition(
    val originalWord: String?,
    val partOfSpeech: String?,
    val pronunciation: String?,
    val translationsArray: List<WordTranslation>?
)