package kosmicbor.mydictionary.ui.mainscreen


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kosmicbor.entities.WordDefinition
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.FragmentMainScreenBinding
import kosmicbor.mydictionary.model.domain.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.scope.Scope

class MainScreenFragment :
    BaseFragment<kosmicbor.giftapp.utils.AppState>(R.layout.fragment_main_screen),
    AndroidScopeComponent {

    companion object {

        @JvmStatic
        fun newInstance() = MainScreenFragment()

        const val TRANSLATION_DIRECTION = "en-ru"
    }

    override val scope: Scope by fragmentScope()

    private val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)
    override val viewModel: MainScreenViewModel by stateViewModel()
    private val mainScope = CoroutineScope(Dispatchers.IO)

    private val recyclerViewAdapter = MainScreenRvAdapter()
    private var lookupWord: String = ""
    private val snackbar by lazy {
        Snackbar.make(binding.root, "Device is offline", Snackbar.LENGTH_INDEFINITE)
    }

    override fun onResume() {

        viewModel.restoreLookupWord()?.let {
            viewModel.getData(it, TRANSLATION_DIRECTION)
        }

        viewModel.checkNetworkStatus()

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

        viewModel.networkStatusToObserve.observe(viewLifecycleOwner) {
            showNetworkStatusMessage(it)
        }
    }

    private fun initRecyclerView() {
        with(binding.mainScreenRecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
    }

    private fun showNetworkStatusMessage(status: Boolean) {

        if (status) {
            if (snackbar.isShown) {
                snackbar.dismiss()
            }
            binding.mainScreenTranslateButton.isEnabled = true

        } else {
            snackbar.show()
            binding.mainScreenTranslateButton.isEnabled = false
        }
    }

    private fun initButtonClickListener() {
        binding.mainScreenTranslateButton.setOnClickListener {

            lookupWord = binding.mainScreenTextInputEditText.text.toString()


            viewModel.getData(lookupWord, TRANSLATION_DIRECTION)

            mainScope.launch {
                if (lookupWord.isNotBlank()) {
                    viewModel.saveWordToDb(lookupWord, TRANSLATION_DIRECTION)
                }
            }
        }
    }

    override fun renderData(appState: kosmicbor.giftapp.utils.AppState) {
        when (appState) {

            is kosmicbor.giftapp.utils.LoadingState -> {
                showProgress()
            }

            is kosmicbor.giftapp.utils.Success<*> -> {

                val data = appState.value as List<WordDefinition>

                showStandardViews()

                if (data.isEmpty()) {
                    binding.mainScreenBackgroundImageview.visibility = View.VISIBLE
                }

                recyclerViewAdapter.updateData(data)
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

