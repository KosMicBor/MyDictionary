package kosmicbor.mydictionary.model.domain

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kosmicbor.mydictionary.R
import kosmicbor.mydictionary.utils.AppState
import kosmicbor.mydictionary.utils.isNetworkAvailable

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T>

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