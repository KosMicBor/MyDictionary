package kosmicbor.mydictionary.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.databinding.ActivityMainBinding
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.domain.BaseActivity
import kosmicbor.mydictionary.ui.mainscreen.MainScreenRvAdapter
import kosmicbor.mydictionary.ui.mainscreen.MainScreenViewModel
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState
import kosmicbor.mydictionary.utils.Success
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class MainActivity : BaseActivity<AppState>() {

    companion object {
        const val TRANSLATION_DIRECTION = "en-ru"
    }

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val recyclerViewAdapter = MainScreenRvAdapter()
    private lateinit var lookupWord: String

    override val viewModel: MainScreenViewModel by stateViewModel()

    override fun onResume() {
        viewModel.restoreLookupWord()?.let {
            viewModel.getData(it, TRANSLATION_DIRECTION)
        }
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkConnection(this@MainActivity)
        initViewModel()
        initRecyclerView()
        initButtonClickListener()

    }

    private fun initViewModel() {
        viewModel.dataToObserve.observe(this) {
            renderData(it)
        }
    }

    private fun initRecyclerView() {
        with(binding.mainScreenRecyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerViewAdapter
        }

    }

    private fun initButtonClickListener() {
        binding.mainScreenTranslateButton.setOnClickListener {

            checkConnection(this@MainActivity)

            lookupWord = binding.mainScreenTextInputEditText.text.toString()

            if (isDeviceOnline) {
                viewModel.getData(lookupWord, TRANSLATION_DIRECTION)
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus

            if (view is TextInputEditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    view.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onStop() {
        if (!lookupWord.isNullOrBlank()) {
            viewModel.saveLookupWord(lookupWord)
        }

        super.onStop()
    }
}