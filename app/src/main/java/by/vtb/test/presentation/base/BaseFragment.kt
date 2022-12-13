package by.vtb.test.presentation.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.vtb.test.di.vm.InjectingSavedStateViewModelFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    /**
     * dagger.Lazy used here, so that injection is request when [getDefaultViewModelProviderFactory] is called
     */
    @Inject
    lateinit var defaultViewModelFactory: dagger.Lazy<InjectingSavedStateViewModelFactory>

    /**
     * This method androidx uses for `by viewModels` method.
     * We can set out injecting factory here and therefore don't touch it again
     */
    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        defaultViewModelFactory.get().create(this, arguments)
}
