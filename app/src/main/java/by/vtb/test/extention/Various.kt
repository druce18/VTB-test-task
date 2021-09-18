package by.vtb.test.extention

import android.content.Context
import by.vtb.test.VtbTestApplication
import by.vtb.test.di.AppComponent

/**
 * Extension for DI
 */
val Context.appComponent: AppComponent
    get() = when (this) {
        is VtbTestApplication -> appComponent
        else -> this.applicationContext.appComponent
    }

fun String.nameFromUrl(): String {
    return this.substring(this.lastIndexOf('/') + 1, this.length)
}
