package com.example.unidad7ruta2ejercicio2.data

import com.example.unidad7ruta2ejercicio2.network.Book
import com.example.unidad7ruta2ejercicio2.network.BookApiService
import com.example.unidad7ruta2ejercicio2.network.BookSearchResponse
import com.example.unidad7ruta2ejercicio2.network.ImageLinks
import com.example.unidad7ruta2ejercicio2.network.VolumeInfo

class FakeBookApiService : BookApiService {

    var searchResponse: BookSearchResponse = BookSearchResponse(items = emptyList())

    var searchException: Exception? = null

    private val booksById = mutableMapOf<String, Book>()

    val searchQueries = mutableListOf<String>()

    val requestedBookIds = mutableListOf<String>()

    fun addBook(id: String, title: String, thumbnail: String?) {
        booksById[id] = Book(
            id = id,
            volumeInfo = VolumeInfo(
                title = title,
                imageLinks = ImageLinks(thumbnail = thumbnail)
            )
        )
    }

    override suspend fun searchBooks(query: String): BookSearchResponse {
        searchQueries += query
        searchException?.let { throw it }
        return searchResponse
    }

    override suspend fun getBook(bookId: String): Book {
        requestedBookIds += bookId
        return booksById[bookId] ?: Book(id = bookId)
    }
}
