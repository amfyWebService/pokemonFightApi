package com.ynov.pokemon.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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

    @Test
    fun `should not select pokemon that doesn't exist in backpack`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70,100, "electric", listOf(attackEclair))
        val ronflex = Pokemon("ronflex", 100,100, "normal", emptyList())
        val backPack = BackPack(listOf(raichu,pikachu), emptyList())
        val trainer = Trainer("Sacha", null, backPack )

        Assertions.assertThrows(IllegalStateException::class.java){
            trainer.pickUpPokemon(ronflex)
        }
    }

    @Test
    fun `should select pokemon that exist in backpack`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(raichu,pikachu), emptyList())
        val trainer = Trainer("Sacha", pikachu, backPack )
        trainer.pickUpPokemon(raichu)

        assertEquals(raichu, trainer.currentPokemon)
    }
}