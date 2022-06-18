package kosmicbor.mydictionary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kosmicbor.mydictionary.model.data.*
import kosmicbor.mydictionary.model.datasource.dto.*
import kosmicbor.mydictionary.model.datasource.room.LocalWordDto

const val ZERO_VAL = 0


//Remote Data transformation
fun convertWordDefinitionDtoToWordDefinition(listDto: List<WordDefinitionDto>): List<WordDefinition> {
    return listDto.map {
        WordDefinition(
            originalWord = it.text,
            partOfSpeech = it.partOfSpeech,
            pronunciation = it.transcription,
            translationsArray = convertWordTranslationDtoToWordTranslation(
                it.translationArray ?: emptyList()
            )
        )
    }
}

fun convertWordTranslationDtoToWordTranslation(list: List<WordTranslationDto>): List<WordTranslation> {
    return list.map {
        WordTranslation(
            translationText = it.translationText,
            translationPartOfSpeech = it.translationPartOfSpeech,
            synonyms = convertSynonymsDtoToSynonyms(it.synonyms ?: emptyList()),
            mean = convertMeanDtoToMean(it.mean ?: emptyList()),
            examples = convertExamplesDtoToExamples(it.examples ?: emptyList())
        )
    }
}

fun convertExamplesDtoToExamples(examplesList: List<ExamplesDto>): List<Example> {
    return examplesList.map {
        Example(
            exampleText = it.exampleText,
            exampleTranslation = convertExampleTranslationDtoToExampleTranslation(it.exampleTranslation),
        )
    }
}

fun convertExampleTranslationDtoToExampleTranslation(
    exampleTranslationList: List<ExampleTranslationDto>
): List<ExampleTranslation> {
    return exampleTranslationList.map {
        ExampleTranslation(
            exampleTranslationText = it.exampleTranslationText
        )
    }
}

fun convertMeanDtoToMean(meansList: List<MeanDto>): List<Mean> {
    return meansList.map {
        Mean(
            meanText = it.meanText
        )
    }
}

fun convertSynonymsDtoToSynonyms(synonymsList: List<SynonymDto>): List<Synonym> {
    return synonymsList.map {
        Synonym(
            synonymText = it.synonymText,
            synonymPartOfSpeech = it.synonymPartOfSpeech,
            synonymGender = it.synonymGender
        )
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

    return networkInfo != null && networkInfo.isConnected
}

//Local Data transformation

fun convertLocalWordToLocalWordDTO(wordObject: LocalWord): LocalWordDto {
    return LocalWordDto(
        id = ZERO_VAL,
        word = wordObject.word,
        translationDirection = wordObject.translationDirection,
        date = wordObject.date
    )

}

//String build methods

fun buildExamplesText(translationsArray: List<WordTranslation>?): String {

    val sb = StringBuilder()

    translationsArray?.forEach {

        it.examples?.forEach { example ->

            sb.append("- ${example.exampleText} ")

            sb.append(
                "(${
                    getExampleTranslations(
                        example.exampleTranslation
                    )
                })"
            )

            sb.append("\n")
        }
    }

    return sb.toString()
}

fun getExampleTranslations(exampleTranslations: List<ExampleTranslation>): String {
    val sb = StringBuilder()

    val iterator = exampleTranslations.iterator()

    while (iterator.hasNext()) {

        sb.append(iterator.next().exampleTranslationText)

        if (iterator.hasNext()) {
            sb.append(", ")
        }
    }

    return sb.toString()
}

fun uniteTranslationOptions(
    label: String?,
    translationsArray: List<WordTranslation>,
): String {
    val sb = StringBuilder()
    sb.append("$label: ")

    val iterator = translationsArray.iterator()

    while (iterator.hasNext()) {

        sb.append(iterator.next().translationText)

        if (iterator.hasNext()) {
            sb.append(", ")
        }
    }

    return sb.toString()
}

fun createStringLine(label: String?, stroke: String?): String {
    val sb = StringBuilder()

    label?.apply {
        sb.append(this)
        sb.append(": ")
    }

    stroke?.apply {
        sb.append(stroke)
    }

    return sb.toString()
}
