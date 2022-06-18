package kosmicbor.mydictionary.ui.historyscreen

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.FragmentHistoryScreenBinding
import kosmicbor.mydictionary.model.data.LocalWord
import kosmicbor.mydictionary.model.domain.BaseFragment
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState
import kosmicbor.mydictionary.utils.Success
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryScreenFragment : BaseFragment<AppState>(R.layout.fragment_history_screen),
    HistoryController {

    companion object {
        fun newInstance() = HistoryScreenFragment()
    }

    private val openFragmentController: OpenFragmentController by lazy {
        activity as OpenFragmentController
    }

    override val viewModel: HistoryScreenViewModel by viewModel()
    private val binding: FragmentHistoryScreenBinding by viewBinding(FragmentHistoryScreenBinding::bind)
    private val historyScreenAdapter: HistoryScreenRvAdapter by lazy {
        HistoryScreenRvAdapter(this) {
            openFragmentController.openWordDescriptionFragment(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToLiveData()
        initRecyclerView()

        viewModel.getData()

        setupWordSearchQuery()
    }

    private fun initRecyclerView() {
        with(binding.historyRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyScreenAdapter
        }
    }

    private fun subscribeToLiveData() {
        viewModel.dataToObserve.observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    override fun renderData(appState: AppState) {
        when (appState) {

            is LoadingState -> {
                showProgress()
            }

            is Success<*> -> {

                val data = appState.value as List<LocalWord>

                showStandardViews()

                if (data.isEmpty()) {
                    binding.historyScreenBackgroundImageview.visibility = View.VISIBLE
                }

                historyScreenAdapter.updateData(data)
            }

            is AppStateError<*> -> {

                appState.error.localizedMessage?.let {
                    Snackbar.make(
                        binding.root,
                        it,
                        Snackbar.LENGTH_SHORT,
                    ).show()
                }

                showStandardViews()
            }
        }
    }

    override fun showProgress() {
        with(binding) {
            historyScreenProgressbar.visibility = View.VISIBLE
            historyScreenBackgroundImageview.visibility = View.GONE
            historyScreenTextInputLayout.visibility = View.GONE
            historyRecyclerView.visibility = View.GONE
        }
    }

    override fun showStandardViews() {
        with(binding) {
            historyScreenProgressbar.visibility = View.GONE
            historyScreenBackgroundImageview.visibility = View.GONE
            historyScreenTextInputLayout.visibility = View.VISIBLE
            historyRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDeleteButtonClick(word: String) {
        viewModel.deleteWordFromDataBase(word)
    }

    private fun setupWordSearchQuery() {

        viewModel.searchWordInHistory()
        binding.historyScreenTextInputEditText.doAfterTextChanged { text ->
            text?.let {
                viewModel.textChangedStateFlow.value = text.toString()
            }
        }
    }
}

interface OpenFragmentController {
    fun openWordDescriptionFragment(bundle: Bundle)
}