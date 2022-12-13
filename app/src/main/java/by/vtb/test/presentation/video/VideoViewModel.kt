package by.vtb.test.presentation.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vtb.test.di.MainDispatcher
import by.vtb.test.di.vm.AssistedSavedStateViewModelFactory
import by.vtb.test.domain.VideoRepository
import by.vtb.test.presentation.base.UiState
import by.vtb.test.presentation.base.getUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoViewModel @AssistedInject constructor(
    private val videoRepository: VideoRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val link = savedStateHandle.getLiveData<String>(ARG_LINK)
    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> = _uiState.asStateFlow()

    init {
        loadVideo()
    }

    fun loadVideo() = viewModelScope.launch(mainDispatcher) {
        link.value?.let { videoUrl ->
            _uiState.value = UiState.Loading
            val result = getUiState {
                videoRepository.loadVideo(videoUrl)
            }
            _uiState.value = result
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<VideoViewModel>

    companion object {

        const val ARG_LINK = "ARG_LINK"
    }
}
