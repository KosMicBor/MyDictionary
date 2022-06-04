package kosmicbor.mydictionary.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.app
import kosmicbor.mydictionary.databinding.ActivityMainBinding
import kosmicbor.mydictionary.model.data.WordDefinition
import kosmicbor.mydictionary.model.domain.DictionaryView
import kosmicbor.mydictionary.model.domain.MainPresenter
import kosmicbor.mydictionary.ui.mainscreen.MainScreenRvAdapter
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.AppStateError
import kosmicbor.mydictionary.utils.LoadingState
import kosmicbor.mydictionary.utils.Success

class MainActivity : AppCompatActivity(), DictionaryView {

    companion object {
        const val TRANSLATION_DIRECTION = "en-ru"
    }

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private lateinit var presenter: MainPresenter<DictionaryView>
    private val recyclerViewAdapter = MainScreenRvAdapter()
    private lateinit var lookupWord: String

    override fun onStart() {
        presenter.attachView(this@MainActivity)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = app.presenter

        presenter.restoreState()

        initRecyclerView()
        initButtonClickListener()

    }

    private fun initRecyclerView() {
        binding.mainScreenRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerViewAdapter
        }

    }

    private fun initButtonClickListener() {
        binding.mainScreenTranslateButton.setOnClickListener {
            lookupWord = binding.mainScreenTextInputEditText.text.toString()
            presenter.saveCurrentLookupWord(lookupWord)
            presenter.getData(lookupWord ?: "", TRANSLATION_DIRECTION)
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
        binding.apply {

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

        binding.apply {

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
        presenter.detachView(this)
        super.onStop()
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
}