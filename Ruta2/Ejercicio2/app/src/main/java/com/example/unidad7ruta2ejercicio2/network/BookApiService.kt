package com.example.unidad7ruta2ejercicio2.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {

    @GET("volumes")
    suspend fun searchBooks(@Query("q") query: String): BookSearchResponse

    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") bookId: String): Book
}
