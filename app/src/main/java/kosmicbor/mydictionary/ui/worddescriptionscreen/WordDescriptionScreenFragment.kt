package kosmicbor.mydictionary.ui.worddescriptionscreen

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kosmicbor.entities.WordDefinition
import kosmicbor.giftapp.utils.getViewById
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.FragmentWordDescriptionScreenBinding
import kosmicbor.mydictionary.model.domain.BaseFragment
import kosmicbor.mydictionary.ui.mainscreen.MainScreenFragment.Companion.TRANSLATION_DIRECTION
import kotlinx.coroutines.launch
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.scope.Scope

class WordDescriptionScreenFragment :
    BaseFragment<kosmicbor.giftapp.utils.AppState>(R.layout.fragment_word_description_screen),
    AndroidScopeComponent {

    companion object {
        const val BUNDLE_WORD_KEY = "BUNDLE_WORD_KEY"
        const val BUNDLE_TRANSITION_DIRECTION_KEY = "BUNDLE_TRANSITION_DIRECTION_KEY"
    }

    override val scope: Scope by fragmentScope()

    private var word: String? = null
    private var transitionDirection: String? = null
    override val viewModel: WordDescriptionScreenViewModel by scope.inject()
    private lateinit var wordDescriptionAdapter: WordDescriptionScreenRvAdapter
    private val binding: FragmentWordDescriptionScreenBinding by viewBinding(
        FragmentWordDescriptionScreenBinding::bind
    )

    private val recyclerView by getViewById<RecyclerView>(R.id.word_description_screen_recyclerview)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBackButton()

        if (savedInstanceState == null) {
            getWordDefinition()
        }

        subscribeToLiveData()
    }

    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun getWordDefinition() {
        word = arguments?.getString(BUNDLE_WORD_KEY)
        transitionDirection = arguments?.getString(BUNDLE_TRANSITION_DIRECTION_KEY)

        viewModel.viewModelScope.launch {
            viewModel.getData(
                word ?: "",
                transitionDirection ?: TRANSLATION_DIRECTION
            )
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

                val data = appState.value as List<WordDefinition>
                wordDescriptionAdapter = WordDescriptionScreenRvAdapter(data)

                showStandardViews()

                with(recyclerView) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = wordDescriptionAdapter
                }
            }

            is kosmicbor.giftapp.utils.AppStateError<*> -> {

                appState.error.localizedMessage?.let {
                    Snackbar.make(
                        binding.root,
                        it,
                        Snackbar.LENGTH_SHORT,
                    )
                        .setAction("Retry") {
                            viewModel.viewModelScope.launch {
                                viewModel.getData(
                                    word ?: "",
                                    transitionDirection ?: TRANSLATION_DIRECTION
                                )
                            }
                        }
                        .show()
                }

                showStandardViews()
            }
        }
    }

    override fun showProgress() {
        binding.wordDescriptionScreenProgressbar.visibility = View.VISIBLE
        binding.wordDescriptionScreenRecyclerview.visibility = View.GONE
    }

    override fun showStandardViews() {
        binding.wordDescriptionScreenProgressbar.visibility = View.GONE
        binding.wordDescriptionScreenRecyclerview.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}