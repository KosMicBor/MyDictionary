package kosmicbor.mydictionary.ui

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import kosmicbor.giftapp.utils.getViewById
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.ui.historyscreen.HistoryScreenFragment
import kosmicbor.mydictionary.ui.historyscreen.OpenFragmentController
import kosmicbor.mydictionary.ui.mainscreen.MainScreenFragment
import kosmicbor.mydictionary.ui.profilescreen.ProfileScreenFragment
import kosmicbor.mydictionary.ui.worddescriptionscreen.WordDescriptionScreenFragment

class MainActivity : AppCompatActivity(), OpenFragmentController {

    companion object {
        private const val SPLASH_SCREEN_ANIMATION_Y = 0f
        private const val SPLASH_SCREEN_ANIMATION_DURATION = 2000L
    }

    private val bottomNavigation by getViewById<BottomNavigationView>(R.id.bottom_navigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.main_container, MainScreenFragment.newInstance())
                .commit()
        }

        setSplashScreenAnimation()

        initBottomMenu()
    }

    private fun setSplashScreenAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->

                val slideLeft = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_X,
                    SPLASH_SCREEN_ANIMATION_Y,
                    -splashScreenView.height.toFloat()
                ).apply {
                    interpolator = AccelerateInterpolator()
                    duration = SPLASH_SCREEN_ANIMATION_DURATION
                    doOnEnd { splashScreenView.remove() }
                }

                slideLeft.start()
            }
        }
    }

    private fun initBottomMenu() {

        bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.bottom_menu_search_button -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_container, MainScreenFragment.newInstance())
                        .addToBackStack("search")
                        .commit()

                    true
                }
                R.id.bottom_menu_history_button -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_container, HistoryScreenFragment.newInstance())
                        .addToBackStack("history")
                        .commit()

                    true
                }

                R.id.bottom_menu_profile_button -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.main_container, ProfileScreenFragment())
                        .addToBackStack("profile")
                        .commit()

                    true
                }
                else -> {
                    false
                }
            }
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

    override fun openWordDescriptionFragment(bundle: Bundle) {

        val fragment = WordDescriptionScreenFragment()

        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack("WordDefinitionFragment")
            .replace(R.id.main_container, fragment)
            .commit()
    }
}