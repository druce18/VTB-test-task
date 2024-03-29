package by.vtb.test.presentation.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vtb.test.di.MainDispatcher
import by.vtb.test.domain.VideoRepository
import by.vtb.test.domain.model.VideoLinks
import by.vtb.test.presentation.base.UiState
import by.vtb.test.presentation.base.getUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoLinksViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<VideoLinks>>(UiState.Loading)
    val uiState: StateFlow<UiState<VideoLinks>> = _uiState.asStateFlow()

    init {
        loadVideoLinks()
    }

    fun loadVideoLinks() = viewModelScope.launch(mainDispatcher) {
        _uiState.value = UiState.Loading
        val result = getUiState {
            videoRepository.videoLinks()
        }
        _uiState.value = result
    }
}
