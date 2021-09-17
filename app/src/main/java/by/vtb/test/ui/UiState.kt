package by.vtb.test.ui

sealed class UiState<out T> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String = "Error") : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}

inline fun <T> getUiState(block: () -> T): UiState<T> {
    return try {
        val data: T = block.invoke()
        UiState.Success(data)
    } catch (e: Exception) {
        UiState.Error()
    }
}
