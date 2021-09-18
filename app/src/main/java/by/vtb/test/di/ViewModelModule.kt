package by.vtb.test.di

import androidx.lifecycle.ViewModel
import by.vtb.test.di.vm.AssistedSavedStateViewModelFactory
import by.vtb.test.di.vm.ViewModelKey
import by.vtb.test.ui.pager.VideoLinksViewModel
import by.vtb.test.ui.video.VideoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(VideoLinksViewModel::class)
    abstract fun bindsVideoLinksViewModel(viewModel: VideoLinksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    abstract fun bindsVideoViewModel(f: VideoViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>
}
