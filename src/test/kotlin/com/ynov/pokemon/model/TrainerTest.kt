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
        val backPack = BackPack(listOf(raichu,pikachu), mutableListOf())
        val trainer = Trainer("Sacha", null, backPack )

        assertNotNull(trainer.currentPokemon)
    }

    @Test
    fun `should not select pokemon that doesn't exist in backpack`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70,100, "electric", listOf(attackEclair))
        val ronflex = Pokemon("ronflex", 100,100, "normal", emptyList())
        val backPack = BackPack(listOf(raichu,pikachu), mutableListOf())
        val trainer = Trainer("Sacha", null, backPack )

        Assertions.assertThrows(IllegalStateException::class.java){
            trainer.pickUpPokemon(ronflex)
        }
    }

    @Test
    fun `should not select pokemon that doesn't exist in backpack on trainer init`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70,100, "electric", listOf(attackEclair))
        val ronflex = Pokemon("ronflex", 100,100, "normal", emptyList())
        val backPack = BackPack(listOf(raichu,pikachu), mutableListOf())

        Assertions.assertThrows(IllegalStateException::class.java){
            Trainer("Sacha", ronflex, backPack )
        }
    }

    @Test
    fun `should select pokemon that exist in backpack`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(raichu,pikachu), mutableListOf())
        val trainer = Trainer("Sacha", pikachu, backPack )
        trainer.pickUpPokemon(raichu)

        assertEquals(raichu, trainer.currentPokemon)
    }

    @Test
    fun `should not select pokemon that is ko`(){
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 100,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 0,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(raichu,pikachu), mutableListOf())
        val trainer = Trainer("Sacha", pikachu, backPack )

        Assertions.assertThrows(IllegalStateException::class.java){
            trainer.pickUpPokemon(raichu)
        }
    }

    @Test
    fun `should select an item and apply it on a pokemon`(){
        val potion = Potion()
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 80,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(pikachu), mutableListOf(potion))
        val trainer = Trainer("Sacha", null, backPack )

        assertEquals(100, trainer.useItem(potion, pikachu).currentHealthPoint)
    }

    @Test
    fun `should not select an item that doesn't exist in the backpack`(){
        val potion = Potion()
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 80,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(pikachu), mutableListOf())
        val trainer = Trainer("Sacha", null, backPack )

        Assertions.assertThrows(IllegalStateException::class.java){
            trainer.useItem(potion, pikachu)
        }
    }

    @Test
    fun `should apply effect only if the pokemon exists in the backpack`(){
        val potion = Potion()
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 80,100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 0,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(pikachu), mutableListOf(potion))
        val trainer = Trainer("Sacha", pikachu, backPack )

        Assertions.assertThrows(IllegalStateException::class.java){
            trainer.useItem(potion, raichu)
        }
    }

    @Test
    fun `should remove an item from the backpack when it used`(){
        val potion = Potion()
        val attackEclair = Attack("éclair", 30)
        val pikachu = Pokemon("pikachu", 80,100, "electric", listOf(attackEclair))
        val backPack = BackPack(listOf(pikachu), mutableListOf(potion))
        val trainer = Trainer("Sacha", null, backPack )
        trainer.useItem(potion, pikachu).currentHealthPoint

        assertEquals(0, trainer.backPack.items.size)
    }

}