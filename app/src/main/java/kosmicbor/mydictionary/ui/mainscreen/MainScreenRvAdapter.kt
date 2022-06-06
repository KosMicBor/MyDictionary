package kosmicbor.mydictionary.ui.mainscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.MainScreenRvItemLayoutBinding
import kosmicbor.mydictionary.model.data.ExampleTranslation
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.data.WordTranslation

class MainScreenRvAdapter : RecyclerView.Adapter<MainScreenRvAdapter.MainScreenViewHolder>() {

    private val translationList: MutableList<WordDefinition> = mutableListOf()

    inner class MainScreenViewHolder(binding: MainScreenRvItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val word = binding.mainScreenRvItemWordTextview
        val wordPartOfSpeech = binding.mainScreenRvItemWordPosTextview
        val wordPronunciation = binding.mainScreenRvItemWordPronunciationTextview
        val wordTranslation = binding.mainScreenRvItemWordTranslationTextview
        val wordExamples = binding.mainScreenRvItemWordExamplesTextview
        val container = binding.mainScreenRvItemContainerLinearlayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {

        val itemBinding = MainScreenRvItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MainScreenViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        with(holder) {
            word.text = translationList[position].originalWord

            wordPartOfSpeech.text =
                createStringLine(
                    this.itemView.context.getString(R.string.part_of_speech_label),
                    if (translationList[position].partOfSpeech.isNullOrBlank()) {
                        this.itemView.context.getString(R.string.no_information_content_text)
                    } else {
                        translationList[position].partOfSpeech
                    }
                )

            wordPronunciation.text =
                createStringLine(
                    this.itemView.context.getString(R.string.pronunciation_label_text),
                    if (translationList[position].pronunciation.isNullOrBlank()) {
                        this.itemView.context.getString(R.string.no_information_content_text)
                    } else {
                        translationList[position].pronunciation
                    }
                )

            wordTranslation.text =
                uniteTranslationOptions(
                    this.itemView.context.getString(R.string.translation_label_text),
                    translationList[position].translationsArray ?: emptyList()
                )

            wordExamples.text = buildExamplesText(translationList[position].translationsArray)
        }
    }

    private fun buildExamplesText(translationsArray: List<WordTranslation>?): String {

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

    private fun getExampleTranslations(exampleTranslations: List<ExampleTranslation>): String {
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

    private fun uniteTranslationOptions(
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

    private fun createStringLine(label: String?, stroke: String?): String {
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

    override fun getItemCount() = translationList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<WordDefinition>) {
        translationList.clear()
        translationList.addAll(list)
        notifyDataSetChanged()
    }
}