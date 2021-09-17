package by.vtb.test.di

import android.content.Context
import by.vtb.test.VtbTestApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: VtbTestApplication): Context = application.applicationContext
}
