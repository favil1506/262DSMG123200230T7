package com.example.unidad7ruta2ejercicio2

import com.example.unidad7ruta2ejercicio2.BuildConfig
import com.example.unidad7ruta2ejercicio2.data.BooksRepository
import com.example.unidad7ruta2ejercicio2.data.GoogleBooksRepository
import com.example.unidad7ruta2ejercicio2.network.ApiKeyInterceptor
import com.example.unidad7ruta2ejercicio2.network.BookApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer : AppContainer {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(BuildConfig.BOOKS_API_KEY))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .baseUrl(BASE_URL)
        .build()

    private val bookApiService: BookApiService by lazy {
        retrofit.create(BookApiService::class.java)
    }

    override val booksRepository: BooksRepository by lazy {
        GoogleBooksRepository(bookApiService)
    }

    private companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }
}
