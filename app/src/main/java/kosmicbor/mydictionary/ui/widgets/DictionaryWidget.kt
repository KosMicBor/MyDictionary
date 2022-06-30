package kosmicbor.mydictionary.ui.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import kosmicbor.entities.LocalWord
import kosmicbor.entities.WordTranslation
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.model.domain.DictionaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import kotlin.random.Random

class DictionaryWidget : AppWidgetProvider() {

    companion object {
        private const val ZERO_VAL = 0
        private const val ONE_VAL = 1
        private const val DICTIONARY_REPOSITORY_NAME = "dictionary_repository"
    }

    private val repository: DictionaryRepository by inject(
        DictionaryRepository::class.java,
        named(DICTIONARY_REPOSITORY_NAME)
    )
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        appWidgetIds.forEach { widgetId ->
            RemoteViews(
                context.packageName,
                R.layout.widget_layout
            ).apply {

                scope.launch {

                    val listOfWords = repository.getLocalDataList()

                    val randomLocalWord = getRandomWord(listOfWords)

                    val stringToShow =
                        getTranslation(randomLocalWord.word, randomLocalWord.translationDirection)

                    this@apply.setTextViewText(
                        R.id.widget_translate_textview,
                        stringToShow
                    )

                    appWidgetManager.updateAppWidget(widgetId, this@apply)

                }
            }
        }
    }

    private fun getRandomWord(listOfWords: List<LocalWord>): LocalWord {

        var randomPosition = ZERO_VAL

        if (listOfWords.isNotEmpty()) {

            randomPosition = Random.nextInt(ZERO_VAL, (listOfWords.size - ONE_VAL))
            println(listOfWords.size)
            println(randomPosition)
        }

        return listOfWords[randomPosition]
    }

    private suspend fun getTranslation(word: String, direction: String): String {

        val data = repository.getWordDefinition(
            word,
            direction
        )

        val randomTranslationsArray = mutableListOf<WordTranslation>()

        if (data.size == ONE_VAL) {
            data[ZERO_VAL].translationsArray?.let { randomTranslationsArray.addAll(it) }

        } else {
            data[Random.nextInt(ZERO_VAL, data.size - ONE_VAL)].translationsArray?.let {

                randomTranslationsArray.addAll(it)

            }
        }

        val translation = randomTranslationsArray[Random.nextInt(
            ZERO_VAL,
            randomTranslationsArray.size - ONE_VAL
        )].translationText

        return if (!translation.isNullOrBlank()) "$word - $translation" else word
    }
}


