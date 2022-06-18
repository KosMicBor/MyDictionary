package kosmicbor.mydictionary.ui.worddescriptionscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.WordDescriptionScreenRvItemBinding
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.utils.buildExamplesText
import kosmicbor.mydictionary.utils.createStringLine
import kosmicbor.mydictionary.utils.uniteTranslationOptions

class WordDescriptionScreenRvAdapter(private val translationList: List<WordDefinition>) :
    RecyclerView.Adapter<WordDescriptionScreenRvAdapter.WordDescriptionScreenViewHolder>() {

    inner class WordDescriptionScreenViewHolder(binding: WordDescriptionScreenRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val word = binding.wordDescriptionScreenRvItemWordTextview
        val wordPartOfSpeech = binding.wordDescriptionScreenRvItemWordPosTextview
        val wordPronunciation = binding.wordDescriptionScreenRvItemWordPronunciationTextview
        val wordTranslation = binding.wordDescriptionScreenRvItemWordTranslationTextview
        val wordExamples = binding.wordDescriptionScreenRvItemWordExamplesTextview
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordDescriptionScreenViewHolder {

        val itemBinding = WordDescriptionScreenRvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WordDescriptionScreenViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WordDescriptionScreenViewHolder, position: Int) {
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
}