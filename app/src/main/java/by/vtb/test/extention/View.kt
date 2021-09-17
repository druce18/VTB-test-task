package by.vtb.test.extention

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.showSnackbarIndefinite(
    @StringRes massage: Int,
    @StringRes nameAction: Int,
    action: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, massage, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction(nameAction) {
        action?.invoke() ?: snackbar.dismiss()
    }
    snackbar.show()
}
