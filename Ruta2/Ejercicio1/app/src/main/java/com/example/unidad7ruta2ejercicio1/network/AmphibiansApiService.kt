package com.example.unidad7ruta2ejercicio1.network

import com.example.unidad7ruta2ejercicio1.model.Amphibian
import retrofit2.http.GET

interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}
