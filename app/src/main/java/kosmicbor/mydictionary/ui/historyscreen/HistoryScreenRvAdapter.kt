package kosmicbor.mydictionary.ui.historyscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.mydictionary.databinding.HistoryRecyclerViewItemBinding
import kosmicbor.entities.LocalWord
import kosmicbor.mydictionary.ui.worddescriptionscreen.WordDescriptionScreenFragment.Companion.BUNDLE_TRANSITION_DIRECTION_KEY
import kosmicbor.mydictionary.ui.worddescriptionscreen.WordDescriptionScreenFragment.Companion.BUNDLE_WORD_KEY

class HistoryScreenRvAdapter(
    private val controller: HistoryController,
    private val onItemClickCallback: (Bundle) -> Unit
) : RecyclerView.Adapter<HistoryScreenRvAdapter.HistoryScreenViewHolder>() {

    private val historyList: MutableList<LocalWord> = mutableListOf()

    inner class HistoryScreenViewHolder(binding: HistoryRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val wordTextView = binding.historyRecyclerViewItemWordTextView
        val dateTextView = binding.historyRecyclerViewItemDateTextView
        val deleteButton = binding.historyRecyclerViewItemDeleteButtonImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryScreenViewHolder {

        val itemBinding = HistoryRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HistoryScreenViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HistoryScreenViewHolder, position: Int) {
        with(holder) {
            wordTextView.text = historyList[position].word
            dateTextView.text = historyList[position].date.toString()

            deleteButton.setOnClickListener {

                controller.onDeleteButtonClick(historyList[position].word)
                val deletingWord = historyList[position]
                historyList.remove(deletingWord)
                notifyItemRemoved(layoutPosition)
            }

            itemView.setOnClickListener {

                val bundle = Bundle()
                bundle.putString(BUNDLE_WORD_KEY, historyList[position].word)
                bundle.putString(
                    BUNDLE_TRANSITION_DIRECTION_KEY,
                    historyList[position].translationDirection
                )

                onItemClickCallback(bundle)
            }
        }
    }

    override fun getItemCount() = historyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<LocalWord>) {
        historyList.clear()
        historyList.addAll(list)
        notifyDataSetChanged()
    }
}

interface HistoryController {
    fun onDeleteButtonClick(word: String)
}