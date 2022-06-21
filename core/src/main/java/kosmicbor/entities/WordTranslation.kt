package kosmicbor.entities

data class WordTranslation (
    val translationText: String?,
    val translationPartOfSpeech: String?,
    val synonyms: List<Synonym>?,
    val mean: List<Mean>?,
    val examples: List<Example>?
)
