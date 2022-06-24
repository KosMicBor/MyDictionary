package kosmicbor.mydictionary.ui.historyscreen

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.FragmentHistoryScreenBinding
import kosmicbor.entities.LocalWord
import kosmicbor.mydictionary.model.domain.BaseFragment
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

class HistoryScreenFragment : BaseFragment<kosmicbor.giftapp.utils.AppState>(R.layout.fragment_history_screen),
    HistoryController, AndroidScopeComponent {

    companion object {
        fun newInstance() = HistoryScreenFragment()
    }

    private val openFragmentController: OpenFragmentController by lazy {
        activity as OpenFragmentController
    }


    override val scope: Scope by fragmentScope()

    override val viewModel: HistoryScreenViewModel by scope.inject()
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

    override fun renderData(appState: kosmicbor.giftapp.utils.AppState) {
        when (appState) {

            is kosmicbor.giftapp.utils.LoadingState -> {
                showProgress()
            }

            is kosmicbor.giftapp.utils.Success<*> -> {

                val data = appState.value as List<LocalWord>

                showStandardViews()

                if (data.isEmpty()) {
                    binding.historyScreenBackgroundImageview.visibility = View.VISIBLE
                }

                historyScreenAdapter.updateData(data)
            }

            is kosmicbor.giftapp.utils.AppStateError<*> -> {

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