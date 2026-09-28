package com.example.unidad7ruta2ejercicio2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.unidad7ruta2ejercicio2.ui.BookshelfScreen
import com.example.unidad7ruta2ejercicio2.ui.theme.Unidad7Ruta2Ejercicio2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unidad7Ruta2Ejercicio2Theme {
                BookshelfScreen()
            }
        }
    }
}
