package com.example.unidad7ruta2ejercicio2.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.unidad7ruta2ejercicio2.BookApplication
import com.example.unidad7ruta2ejercicio2.data.BooksRepository
import com.example.unidad7ruta2ejercicio2.network.Book
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import retrofit2.HttpException

enum class BookshelfUiStatus {
    Loading,
    Success,
    Empty,
    Error
}

private const val DEFAULT_QUERY = "jazz history"

data class BookshelfUiState(
    val query: String = DEFAULT_QUERY,
    val books: List<Book> = emptyList(),
    val status: BookshelfUiStatus = BookshelfUiStatus.Loading,
    val errorMessage: String? = null
)

class BookshelfViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    var uiState by mutableStateOf(BookshelfUiState())
        private set

    init {
        searchBooks()
    }

    fun onQueryChange(newQuery: String) {
        uiState = uiState.copy(query = newQuery)
    }

    fun searchBooks() {
        val query = uiState.query.trim().ifBlank { DEFAULT_QUERY }
        uiState = uiState.copy(query = query, status = BookshelfUiStatus.Loading, errorMessage = null)
        viewModelScope.launch {
            try {
                val books = booksRepository.getBooks(query)
                uiState = uiState.copy(
                    books = books,
                    status = if (books.isEmpty()) BookshelfUiStatus.Empty else BookshelfUiStatus.Success
                )
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (httpException: HttpException) {
                uiState = uiState.copy(
                    status = BookshelfUiStatus.Error,
                    errorMessage = httpErrorMessage(httpException.code())
                )
            } catch (exception: Exception) {
                uiState = uiState.copy(
                    status = BookshelfUiStatus.Error,
                    errorMessage = exception.message
                )
            }
        }
    }

    private fun httpErrorMessage(code: Int): String = when (code) {
        429 -> "HTTP 429: cuota diaria de Google Books agotada. " +
            "Usa tu propia API key (BOOKS_API_KEY en local.properties) o inténtalo más tarde."
        403 -> "HTTP 403: acceso denegado. Revisa tu API key."
        else -> "HTTP $code"
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val appContainer = (application as BookApplication).appContainer
                BookshelfViewModel(appContainer.booksRepository)
            }
        }
    }
}
