package com.example.unidad7ruta2ejercicio2

import android.app.Application

class BookApplication : Application() {

    val appContainer: AppContainer by lazy { DefaultAppContainer() }
}
