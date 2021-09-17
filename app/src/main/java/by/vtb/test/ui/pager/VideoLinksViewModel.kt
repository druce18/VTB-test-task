package by.vtb.test.ui.pager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.vtb.test.repository.VideoLinksRepository
import by.vtb.test.repository.model.VideoLinks
import by.vtb.test.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideoLinksViewModel @Inject constructor(
    private val videoLinksRepository: VideoLinksRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<VideoLinks>>(UiState.Loading)
    val uiState: StateFlow<UiState<VideoLinks>> = _uiState.asStateFlow()

    init {
        loadVideoLinks()
    }

    fun loadVideoLinks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = try {
                val videoLinks = videoLinksRepository.videoLinks()
                UiState.Success(videoLinks)
            } catch (e: Exception) {
                UiState.Error()
            }
            _uiState.value = result
        }
    }
}