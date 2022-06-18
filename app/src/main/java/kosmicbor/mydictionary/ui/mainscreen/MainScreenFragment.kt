package kosmicbor.mydictionary.ui.mainscreen


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.FragmentMainScreenBinding
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.domain.BaseFragment
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState
import kosmicbor.mydictionary.utils.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class MainScreenFragment : BaseFragment<AppState>(R.layout.fragment_main_screen) {

    companion object {

        @JvmStatic
        fun newInstance() = MainScreenFragment()

        const val TRANSLATION_DIRECTION = "en-ru"
    }

    private val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)
    override val viewModel: MainScreenViewModel by stateViewModel()
    private val mainScope = CoroutineScope(Dispatchers.IO)

    private val recyclerViewAdapter = MainScreenRvAdapter()
    private var lookupWord: String = ""

    override fun onResume() {

        viewModel.restoreLookupWord()?.let {
            viewModel.getData(it, TRANSLATION_DIRECTION)
        }

        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRecyclerView()
        initButtonClickListener()
    }

    private fun initViewModel() {
        viewModel.dataToObserve.observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun initRecyclerView() {
        with(binding.mainScreenRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
    }

    private fun initButtonClickListener() {
        binding.mainScreenTranslateButton.setOnClickListener {

            checkConnection(requireActivity())

            lookupWord = binding.mainScreenTextInputEditText.text.toString()

            if (isDeviceOnline) {
                viewModel.getData(lookupWord, TRANSLATION_DIRECTION)

                mainScope.launch {
                    if (lookupWord.isNotBlank()) {
                        viewModel.saveWordToDb(lookupWord, TRANSLATION_DIRECTION)
                    }
                }
            }
        }
    }

    override fun renderData(appState: AppState) {
        when (appState) {

            is LoadingState -> {
                showProgress()
            }

            is Success<*> -> {

                val data = appState.value as List<WordDefinition>

                showStandardViews()

                if (data.isEmpty()) {
                    binding.mainScreenBackgroundImageview.visibility = View.VISIBLE
                }

                recyclerViewAdapter.updateData(data)
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

                binding.mainScreenBackgroundImageview.visibility = View.VISIBLE
            }
        }
    }

    override fun showProgress() {
        with(binding) {

            if (mainScreenBackgroundImageview.visibility == View.VISIBLE) {
                mainScreenBackgroundImageview.visibility = View.GONE
            }

            mainScreenTranslateButton.visibility = View.GONE
            mainScreenTextInputLayout.visibility = View.GONE
            mainScreenRecyclerView.visibility = View.GONE
            mainScreenProgressbar.visibility = View.VISIBLE
        }
    }

    override fun showStandardViews() {
        with(binding) {

            if (mainScreenBackgroundImageview.visibility == View.VISIBLE) {
                mainScreenBackgroundImageview.visibility = View.GONE
            }

            mainScreenTranslateButton.visibility = View.VISIBLE
            mainScreenTextInputLayout.visibility = View.VISIBLE
            mainScreenRecyclerView.visibility = View.VISIBLE
            mainScreenProgressbar.visibility = View.GONE
        }
    }

    override fun onStop() {

        if (lookupWord.isNotBlank()) {
            viewModel.saveLookupWord(lookupWord)
        }

        mainScope.cancel()

        super.onStop()
    }
}

