package com.example.unidad7ruta2ejercicio1.data

import com.example.unidad7ruta2ejercicio1.model.Amphibian
import com.example.unidad7ruta2ejercicio1.network.AmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class DefaultAmphibiansRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> =
        amphibiansApiService.getAmphibians().map { it.toSpanish() }
}
