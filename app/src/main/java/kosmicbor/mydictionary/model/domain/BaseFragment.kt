package kosmicbor.mydictionary.model.domain

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kosmicbor.giftapp.utils.AppState
import kosmicbor.giftapp.utils.isNetworkAvailable
import kosmicbor.mydictionary.R

abstract class BaseFragment<T : AppState>(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {

    abstract val viewModel: ViewModel

    protected var isDeviceOnline: Boolean = false

    protected fun checkConnection(context: Context) {
        isDeviceOnline = isNetworkAvailable(context)

        if (!isDeviceOnline) {
            showConnectionAlertDialog(context)
        }
    }

    private fun showConnectionAlertDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(getString(R.string.no_connection_alert_dialog_title))
            .setMessage(getString(R.string.no_connection_alert_dialog_message))
            .setNeutralButton(getString(R.string.no_connection_alert_dialog_button_ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    abstract fun renderData(appState: T)
    abstract fun showProgress()
    abstract fun showStandardViews()
}