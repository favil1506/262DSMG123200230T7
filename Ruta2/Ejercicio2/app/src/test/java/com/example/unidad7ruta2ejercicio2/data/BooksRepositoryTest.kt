package com.example.unidad7ruta2ejercicio2.data

import com.example.unidad7ruta2ejercicio2.network.Book
import com.example.unidad7ruta2ejercicio2.network.BookSearchResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class BooksRepositoryTest {

    private lateinit var fakeBookApiService: FakeBookApiService
    private lateinit var repository: BooksRepository

    @Before
    fun setUp() {
        fakeBookApiService = FakeBookApiService()
        repository = GoogleBooksRepository(fakeBookApiService)
    }

    @Test
    fun booksRepository_getBooks_returnsBooksFromService() = runTest {
        fakeBookApiService.searchResponse = BookSearchResponse(
            totalItems = 2,
            items = listOf(Book(id = "1"), Book(id = "2"))
        )
        fakeBookApiService.addBook(
            id = "1",
            title = "The History of Jazz",
            thumbnail = "http://books.google.com/books/1"
        )
        fakeBookApiService.addBook(
            id = "2",
            title = "Jazz Styles",
            thumbnail = "http://books.google.com/books/2"
        )

        val books = repository.getBooks("jazz history")

        assertEquals(2, books.size)
        assertEquals("The History of Jazz", books[0].volumeInfo?.title)
        assertEquals("Jazz Styles", books[1].volumeInfo?.title)
        assertEquals(listOf("jazz history"), fakeBookApiService.searchQueries)
        assertEquals(listOf("1", "2"), fakeBookApiService.requestedBookIds)
    }

    @Test
    fun booksRepository_getBooks_replacesHttpWithHttps() = runTest {
        fakeBookApiService.searchResponse = BookSearchResponse(
            totalItems = 1,
            items = listOf(Book(id = "1"))
        )
        fakeBookApiService.addBook(
            id = "1",
            title = "Kansas City Jazz",
            thumbnail = "http://books.google.com/books/1"
        )

        val books = repository.getBooks("jazz")

        val thumbnail = books.single().volumeInfo?.imageLinks?.thumbnail
        assertEquals("https://books.google.com/books/1", thumbnail)
        assertTrue(thumbnail?.startsWith("https://") == true)
    }

    @Test
    fun booksRepository_getBooks_keepsHttpsThumbnails() = runTest {
        fakeBookApiService.searchResponse = BookSearchResponse(
            totalItems = 1,
            items = listOf(Book(id = "1"))
        )
        fakeBookApiService.addBook(
            id = "1",
            title = "Jazz Overview",
            thumbnail = "https://books.google.com/books/1"
        )

        val books = repository.getBooks("jazz")

        assertEquals("https://books.google.com/books/1", books.single().volumeInfo?.imageLinks?.thumbnail)
    }

    @Test
    fun booksRepository_getBooks_returnsEmptyList_whenNoResults() = runTest {
        fakeBookApiService.searchResponse = BookSearchResponse(totalItems = 0, items = null)

        val books = repository.getBooks("zzzz")

        assertTrue(books.isEmpty())
        assertTrue(fakeBookApiService.requestedBookIds.isEmpty())
    }

    @Test
    fun booksRepository_getBooks_handlesBookWithoutImageLinks() = runTest {
        fakeBookApiService.searchResponse = BookSearchResponse(
            totalItems = 1,
            items = listOf(Book(id = "1"))
        )

        val books = repository.getBooks("jazz")

        assertEquals(1, books.size)
        assertTrue(books.single().volumeInfo?.imageLinks == null)
    }

    @Test(expected = IOException::class)
    fun booksRepository_getBooks_propagatesNetworkErrors() = runTest {
        fakeBookApiService.searchResponse = BookSearchResponse(
            totalItems = 1,
            items = listOf(Book(id = "1"))
        )
        fakeBookApiService.searchException = IOException("No connection")

        repository.getBooks("jazz")
    }
}
