package by.vtb.test

import android.app.Application
import by.vtb.test.di.AppComponent
import by.vtb.test.di.DaggerAppComponent
import timber.log.Timber

class VtbTestApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
