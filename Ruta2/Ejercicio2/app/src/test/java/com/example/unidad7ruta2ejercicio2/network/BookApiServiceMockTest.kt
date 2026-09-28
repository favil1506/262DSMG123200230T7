package com.example.unidad7ruta2ejercicio2.network

import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookApiServiceMockTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun buildService(apiKey: String? = null): BookApiService {
        val clientBuilder = OkHttpClient.Builder()
        if (apiKey != null) {
            clientBuilder.addInterceptor(ApiKeyInterceptor(apiKey))
        }
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(BookApiService::class.java)
    }

    @Test
    fun bookApiService_searchBooks_sendsQueryAndParsesIds() = runTest {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
                {
                  "totalItems": 2,
                  "items": [
                    { "id": "1", "volumeInfo": { "title": "Book one" } },
                    { "id": "2", "volumeInfo": { "title": "Book two" } }
                  ]
                }
                """.trimIndent()
            )
        )

        val response = buildService().searchBooks("jazz history")

        assertEquals(2, response.items?.size)
        assertEquals("1", response.items?.get(0)?.id)
        assertEquals("Book one", response.items?.get(0)?.volumeInfo?.title)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/volumes", recordedRequest.path?.substringBefore("?"))
        assertEquals("jazz history", recordedRequest.requestUrl?.queryParameter("q"))
        assertNull(recordedRequest.requestUrl?.queryParameter("key"))
    }

    @Test
    fun bookApiService_getBook_parsesThumbnail() = runTest {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
                {
                  "id": "abc",
                  "volumeInfo": {
                    "title": "Kansas City Jazz",
                    "imageLinks": { "thumbnail": "http://books.google.com/books/abc" }
                  }
                }
                """.trimIndent()
            )
        )

        val book = buildService().getBook("abc")

        assertEquals("abc", book.id)
        assertEquals("Kansas City Jazz", book.volumeInfo?.title)
        assertEquals("http://books.google.com/books/abc", book.volumeInfo?.imageLinks?.thumbnail)
        assertEquals("/volumes/abc", mockWebServer.takeRequest().path)
    }

    @Test
    fun apiKeyInterceptor_addsKeyToRequest() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("""{ "totalItems": 0 }"""))

        buildService(apiKey = "TEST_KEY").searchBooks("jazz")

        assertEquals(
            "TEST_KEY",
            mockWebServer.takeRequest().requestUrl?.queryParameter("key")
        )
    }

    @Test
    fun apiKeyInterceptor_omitsKeyWhenBlank() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("""{ "totalItems": 0 }"""))

        buildService(apiKey = "").searchBooks("jazz")

        assertNull(mockWebServer.takeRequest().requestUrl?.queryParameter("key"))
    }
}
