package com.example.unidad7ruta2ejercicio2.data

import com.example.unidad7ruta2ejercicio2.network.Book
import com.example.unidad7ruta2ejercicio2.network.BookApiService

interface BooksRepository {

    suspend fun getBooks(query: String): List<Book>
}

class GoogleBooksRepository(
    private val bookApiService: BookApiService
) : BooksRepository {

    override suspend fun getBooks(query: String): List<Book> {
        val searchResponse = bookApiService.searchBooks(query)
        val books = mutableListOf<Book>()
        for (item in searchResponse.items.orEmpty()) {
            books += bookApiService.getBook(item.id)
        }
        return books.map { it.withHttpsThumbnail() }
    }

    private fun Book.withHttpsThumbnail(): Book {
        val volumeInfo = this.volumeInfo ?: return this
        val imageLinks = volumeInfo.imageLinks ?: return this
        val thumbnail = imageLinks.thumbnail ?: return this
        return copy(
            volumeInfo = volumeInfo.copy(
                imageLinks = imageLinks.copy(
                    thumbnail = thumbnail.replace("http://", "https://")
                )
            )
        )
    }
}
