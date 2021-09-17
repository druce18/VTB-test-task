package by.vtb.test.ui.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vtb.test.repository.VideoRepository
import by.vtb.test.ui.UiState
import by.vtb.test.ui.getUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)
    val uiState: StateFlow<UiState<String>> = _uiState.asStateFlow()

    fun loadVideo(videoUrl: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getUiState {
                videoRepository.loadVideo(videoUrl)
            }
            _uiState.value = result
        }
    }
}
