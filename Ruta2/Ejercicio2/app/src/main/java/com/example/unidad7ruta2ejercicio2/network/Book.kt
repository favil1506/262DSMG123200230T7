package com.example.unidad7ruta2ejercicio2.network

data class BookSearchResponse(
    val totalItems: Int = 0,
    val items: List<Book>? = null
)

data class Book(
    val id: String = "",
    val volumeInfo: VolumeInfo? = null
)

data class VolumeInfo(
    val title: String = "",
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null
)

data class ImageLinks(
    val thumbnail: String? = null
)
