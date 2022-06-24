package kosmicbor.giftapp.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import kosmicbor.dto.*
import kosmicbor.entities.*
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

const val ZERO_VAL = 0
const val NO_ROOT_MESSAGE = "Cannot get View, there is no root yet"

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


class ViewByIdDelegate<out T : View>(private val rootGetter: () -> View?, private val viewId: Int) {
    private var rootRef: WeakReference<View>? = null
    private var viewRef: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        var view = viewRef
        val cachedRoot = rootRef?.get()

        val currentRoot = rootGetter()

        if (currentRoot != cachedRoot || view == null) {

            if (currentRoot == null) {
                if (view != null) {
                    return view
                }

                throw IllegalStateException(
                    NO_ROOT_MESSAGE
                )
            }

            view = currentRoot.findViewById(viewId)

            viewRef = view
            rootRef = WeakReference(currentRoot)
        }

        checkNotNull(view) { "View with ID $viewId not found in root" }

        return view
    }
}

fun <T : View> Activity.getViewById(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({ window.decorView.findViewById(android.R.id.content) }, viewId)
}

fun <T : View> Fragment.getViewById(@IdRes viewId: Int): ViewByIdDelegate<T> {
    return ViewByIdDelegate({ view }, viewId)
}