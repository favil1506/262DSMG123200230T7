package com.example.unidad7ruta2ejercicio1

import com.example.unidad7ruta2ejercicio1.data.toSpanish
import com.example.unidad7ruta2ejercicio1.model.Amphibian
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AmphibianTranslationsTest {

    private val englishAmphibians = listOf(
        Amphibian("Great Basin Spadefoot", "Toad", "This toad spends most of its life underground.", "img1"),
        Amphibian("Roraima Bush Toad", "Toad", "This toad is typically found in South America.", "img2"),
        Amphibian("Pacific Chorus Frog", "Frog", "Also known as the Pacific Treefrog.", "img3"),
        Amphibian("Blue Jeans Frog", "Frog", "Sometimes called the Strawberry Poison-Dart Frog.", "img4"),
        Amphibian("California Giant Salamander", "Salamander", "As the name implies, this salamander.", "img5"),
        Amphibian("Tiger Salamander", "Salamander", "Tiger Salamanders are typically found.", "img6")
    )

    @Test
    fun amphibians_are_translated_to_spanish() {
        val translated = englishAmphibians.map { it.toSpanish() }

        val types = translated.map { it.type }.toSet()
        assertEquals(setOf("Sapo", "Rana", "Salamandra"), types)

        assertTrue(translated.all { it.name.isNotBlank() })
        assertTrue(translated.none { it.description.contains("This ") })
        assertTrue(translated.none { it.name == "Great Basin Spadefoot" })
    }

    @Test
    fun unknown_amphibian_is_kept_unchanged() {
        val unknown = Amphibian("Unknown Species", "Frog", "Description", "img")

        assertEquals(unknown, unknown.toSpanish())
    }

    @Test
    fun images_are_not_changed_by_translation() {
        val original = englishAmphibians[0]

        assertEquals(original.imgSrc, original.toSpanish().imgSrc)
    }
}
