package com.example.unidad7ruta2ejercicio2.network

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GoogleBooksParsingTest {

    private val gson = Gson()

    @Test
    fun gson_parsesSearchResponse() {
        val json = """
            {
              "kind": "books#volumes",
              "totalItems": 2,
              "items": [
                {
                  "kind": "books#volume",
                  "id": "EPUTEAAAQBAJ",
                  "volumeInfo": {
                    "title": "An Introduction to Jazz History",
                    "authors": ["Donald D. Megill", "Richard S. Darcy"],
                    "publishedDate": "2017",
                    "imageLinks": {
                      "smallThumbnail": "http://books.google.com/books/publisher/content?id=EPUTEAAAQBAJ&zoom=5",
                      "thumbnail": "http://books.google.com/books/publisher/content?id=EPUTEAAAQBAJ&zoom=1"
                    }
                  }
                },
                {
                  "kind": "books#volume",
                  "id": "s1gVAAAAYAAJ",
                  "volumeInfo": {
                    "title": "Jazz Styles"
                  }
                }
              ]
            }
        """.trimIndent()

        val response = gson.fromJson(json, BookSearchResponse::class.java)

        assertEquals(2, response.totalItems)
        assertEquals(2, response.items?.size)
        assertEquals("EPUTEAAAQBAJ", response.items?.get(0)?.id)
        assertEquals("An Introduction to Jazz History", response.items?.get(0)?.volumeInfo?.title)
        assertEquals(
            "http://books.google.com/books/publisher/content?id=EPUTEAAAQBAJ&zoom=1",
            response.items?.get(0)?.volumeInfo?.imageLinks?.thumbnail
        )
        assertNull(response.items?.get(1)?.volumeInfo?.imageLinks)
    }

    @Test
    fun gson_parsesSingleBook() {
        val json = """
            {
              "kind": "books#volume",
              "id": "abc123",
              "volumeInfo": {
                "title": "Kansas City Jazz",
                "authors": ["Dwight Brantley"],
                "imageLinks": {
                  "thumbnail": "http://books.google.com/books/content?id=abc123&zoom=1"
                }
              },
              "accessInfo": { "viewability": "PARTIAL" }
            }
        """.trimIndent()

        val book = gson.fromJson(json, Book::class.java)

        assertEquals("abc123", book.id)
        assertEquals("Kansas City Jazz", book.volumeInfo?.title)
        assertEquals(listOf("Dwight Brantley"), book.volumeInfo?.authors)
        assertTrue(book.volumeInfo?.imageLinks?.thumbnail?.startsWith("http://") == true)
    }

    @Test
    fun gson_parsesResponseWithoutItems() {
        val json = """{ "totalItems": 0 }"""

        val response = gson.fromJson(json, BookSearchResponse::class.java)

        assertEquals(0, response.totalItems)
        assertNull(response.items)
    }
}
