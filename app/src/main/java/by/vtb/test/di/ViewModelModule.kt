package by.vtb.test.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.vtb.test.ui.pager.VideoLinksViewModel
import by.vtb.test.ui.video.VideoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(VideoLinksViewModel::class)
    abstract fun videoLinksViewModel(viewModel: VideoLinksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    abstract fun videoViewModel(viewModel: VideoViewModel): ViewModel
}
