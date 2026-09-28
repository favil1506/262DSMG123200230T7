package com.example.unidad7ruta2ejercicio1

import android.app.Application
import com.example.unidad7ruta2ejercicio1.data.AppContainer
import com.example.unidad7ruta2ejercicio1.data.DefaultAppContainer

class AmphibiansApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
