package com.ynov.pokemon.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PotionTest {

    @Test
    fun `should increase pokemon health of 20 points`() {
        val potion = Potion()
        val salameche = Pokemon("Salameche", 50, 100, "feu", listOf())

        potion.apply(salameche)

        assertEquals(70, salameche.currentHealthPoint)
    }
}