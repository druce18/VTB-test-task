package by.vtb.test.presentation.base

import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class UiState<out T> {
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}

inline fun <T> getUiState(block: () -> T): UiState<T> {
    return try {
        val data: T = block.invoke()
        UiState.Success(data)
    } catch (exception: Exception) {
        Timber.e(exception)
        val massage = when (exception) {
            is ConnectException -> "No network connection!"
            is SocketTimeoutException -> "No connection!"
            is UnknownHostException -> "Server is not available!"
            is HttpException -> "HTTP call error!"
            is NullPointerException -> "Missing file!"
            is IOException -> "Failed to save file!"
            else -> "Unknown Error!"
        }
        UiState.Error(massage)
    }
}
