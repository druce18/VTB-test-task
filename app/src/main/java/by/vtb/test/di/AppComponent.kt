package by.vtb.test.di

import by.vtb.test.VtbTestApplication
import by.vtb.test.presentation.MainActivity
import by.vtb.test.presentation.pager.VideoLinksFragment
import by.vtb.test.presentation.video.VideoFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppModuleBind::class,
        NetworkModule::class,
        ViewModelModule::class,
        DispatcherModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: VtbTestApplication): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: VideoLinksFragment)

    fun inject(fragment: VideoFragment)
}
