package com.example.unidad7ruta2ejercicio1.data

import com.example.unidad7ruta2ejercicio1.model.Amphibian

private data class SpanishAmphibian(
    val name: String,
    val type: String,
    val description: String
)

private val spanishByName = mapOf(
    "Great Basin Spadefoot" to SpanishAmphibian(
        name = "Sapo de la Gran Cuenca",
        type = "Sapo",
        description = "Este sapo pasa la mayor parte de su vida bajo tierra debido a las " +
                "áridas condiciones del desierto en el que vive. Los sapos de pala deben su " +
                "nombre a sus patas traseras, adaptadas para excavar. Por lo general son de " +
                "color gris, verde o marrón con manchas oscuras."
    ),
    "Roraima Bush Toad" to SpanishAmphibian(
        name = "Sapo del Monte Roraima",
        type = "Sapo",
        description = "Este sapo se encuentra habitualmente en Sudamérica, específicamente " +
                "en el monte Roraima, en las fronteras de Venezuela, Brasil y Guyana, de donde " +
                "toma su nombre. El sapo del monte Roraima suele ser negro con manchas amarillas " +
                "o vetas a lo largo de la garganta y el vientre."
    ),
    "Pacific Chorus Frog" to SpanishAmphibian(
        name = "Rana cantora del Pacífico",
        type = "Rana",
        description = "También conocida como rana arborícola del Pacífico, es la rana más " +
                "común de la costa oeste de Norte América. Estas ranas pueden variar en color " +
                "entre verde y marrón, y se identifican por una franja marrón que va desde su " +
                "fosa nasal, pasa por el oído."
    ),
    "Blue Jeans Frog" to SpanishAmphibian(
        name = "Rana de mezclilla",
        type = "Rana",
        description = "A veces llamada rana venenosa de fresa, este pequeño anfibio se " +
                "distingue por su cuerpo rojo brillante y sus brazos y patas de color azul " +
                "morado. La rana de mezclilla no es tóxica para los humanos, como algunos de " +
                "sus parientes cercanos, pero puede ser dañina para algunos depredadores."
    ),
    "California Giant Salamander" to SpanishAmphibian(
        name = "Salamandra gigante de California",
        type = "Salamandra",
        description = "Como su nombre indica, esta salamandra se encuentra en el norte de " +
                "California, así como en otras partes del noroeste del Pacífico. Prefiere áreas " +
                "templadas con mucha humedad y puede crecer hasta 30 cm."
    ),
    "Tiger Salamander" to SpanishAmphibian(
        name = "Salamandra tigre",
        type = "Salamandra",
        description = "Las salamandras tigre suelen encontrarse en la costa atlántica de " +
                "Norte América. Deben su nombre a su coloración: un cuerpo marrón con manchas " +
                "amarillentas verdosas. Aunque les gustan los ambientes húmedos, no pasan mucho " +
                "tiempo en cuerpos de agua; prefieren excavar en el suelo suelto."
    )
)

fun Amphibian.toSpanish(): Amphibian {
    val translated = spanishByName[name] ?: return this
    return copy(
        name = translated.name,
        type = translated.type,
        description = translated.description
    )
}
