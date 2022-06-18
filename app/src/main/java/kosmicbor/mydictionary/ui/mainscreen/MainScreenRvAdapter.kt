package kosmicbor.mydictionary.ui.mainscreen

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.MainScreenRvItemLayoutBinding
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.utils.buildExamplesText
import kosmicbor.mydictionary.utils.createStringLine
import kosmicbor.mydictionary.utils.uniteTranslationOptions

class MainScreenRvAdapter : RecyclerView.Adapter<MainScreenRvAdapter.MainScreenViewHolder>() {

    private val translationList: MutableList<WordDefinition> = mutableListOf()

    inner class MainScreenViewHolder(binding: MainScreenRvItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val word = binding.mainScreenRvItemWordTextview
        val wordPartOfSpeech = binding.mainScreenRvItemWordPosTextview
        val wordPronunciation = binding.mainScreenRvItemWordPronunciationTextview
        val wordTranslation = binding.mainScreenRvItemWordTranslationTextview
        val wordExamples = binding.mainScreenRvItemWordExamplesTextview
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

    override fun getItemCount() = translationList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<WordDefinition>) {
        translationList.clear()
        translationList.addAll(list)
        notifyDataSetChanged()
    }
}