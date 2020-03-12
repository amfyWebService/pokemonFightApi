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

    @Test
    fun `should not overtaken maxHealthPoint of the pokemon`() {
        val potion = Potion()
        val salameche = Pokemon("Salameche", 90, 100, "feu", listOf())

        potion.apply(salameche)

        assertEquals(100, salameche.currentHealthPoint)
    }
}