package by.vtb.test.extention

import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import by.vtb.test.R
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

fun View.showSnackbarErrorIndefinite(
    massage: String,
    @StringRes nameAction: Int,
    action: (() -> Unit)? = null
) {
    val snackbar = Snackbar.make(this, massage, Snackbar.LENGTH_INDEFINITE)
    with(snackbar) {
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        val snackbarTextView: TextView = view.findViewById(R.id.snackbar_text)
        snackbarTextView.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.snackbar_text)
        )
        val redColor = ContextCompat.getColor(context, R.color.red_error)
        setTextColor(redColor)
        setActionTextColor(redColor)
        setAction(nameAction) {
            action?.invoke() ?: dismiss()
        }
        show()
    }
}
