package kosmicbor.mydictionary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kosmicbor.mydictionary.model.data.*
import kosmicbor.mydictionary.model.datasource.dto.*

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
