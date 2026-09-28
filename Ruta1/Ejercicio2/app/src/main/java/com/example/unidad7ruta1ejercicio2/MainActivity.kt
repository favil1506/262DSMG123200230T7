package com.example.unidad7ruta1ejercicio2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.unidad7ruta1ejercicio2.ui.MarsPhotosApp
import com.example.unidad7ruta1ejercicio2.ui.theme.Unidad7Ruta1Ejercicio2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unidad7Ruta1Ejercicio2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MarsPhotosApp()
                }
            }
        }
    }
}
