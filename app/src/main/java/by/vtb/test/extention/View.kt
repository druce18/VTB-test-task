package by.vtb.test.extention

import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setInvisible() {
    visibility = View.INVISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.showSnackbarLong(@StringRes resId: Int) {
    Snackbar.make(this, resId, Snackbar.LENGTH_LONG).show()
}

fun View.delayOnLifecycle(
    durationInMillis: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: () -> Unit
): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
    lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
        delay(durationInMillis)
        block()
    }
}
