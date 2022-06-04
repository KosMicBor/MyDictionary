package kosmicbor.mydictionary.utils

import android.util.Log
import kosmicbor.mydictionary.model.data.*
import kosmicbor.mydictionary.model.datasource.dto.*

fun convertWordDefinitionDtoToWordDefinition(listDto: List<WordDefinitionDto>): List<WordDefinition> {
    Log.d("@@@", "Convert $listDto")
    return listDto.map {
        WordDefinition(
            originalWord = it.text,
            partOfSpeech = it.partOfSpeech,
            pronunciation = it.transcription,
            translationsArray = convertWordTranslationDtoToWordTranslation(it.translationArray ?: emptyList())
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
