package com.ynov.pokemon.model

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class TrainerTest{

    @Test
    fun `should have a default pokemon`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(raichu,pikachu), emptyList())
        val trainer = Trainer("Sacha", null, backPack )

        assertNotNull(trainer.currentPokemon)
    }
}