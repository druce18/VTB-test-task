package by.vtb.test.ui

sealed class UiState<out R> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String = "Error") : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}
