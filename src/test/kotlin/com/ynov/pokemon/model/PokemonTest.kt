package com.ynov.pokemon.model

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PokemonTest{

    @Test
    fun `should attack a pokemon`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70,100, "electric", listOf(attackEclair))
        pikachu.attack(raichu, attackEclair)

        assertEquals(40, raichu.currentHealthPoint)

    }

    @Test
    fun `shloud apply damage on pokemon`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        pikachu.applyDamage(10)

        assertEquals(90, pikachu.currentHealthPoint)
    }


}

