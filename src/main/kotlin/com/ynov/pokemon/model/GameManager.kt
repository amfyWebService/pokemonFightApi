package com.ynov.pokemon.model

import com.lectra.koson.arr
import com.lectra.koson.obj

class GameManager(val player: Trainer, val ai: Trainer) {
    fun gameState(): String {
        return obj {
            "player1" to player.toJson()
            "player2" to ai.toJson()
        }.toString()
    }

    private fun isAllPokemonsKo(trainer: Trainer): Boolean {
        return false
    }
}

fun Trainer.toJson() = obj {
    "name" to name
    "pokemons" to arr[backPack.pokemons.map { pokemon ->
        obj {
            "name" to pokemon.name
            "type" to pokemon.type
            "currentHealthPoint" to pokemon.currentHealthPoint
            "maxHealthPoint" to pokemon.maxHealthPoint
            "attacks" to arr[pokemon.attacks.map { attack ->
                obj {
                    "name" to attack.name
                    "damage" to attack.damage
                }
            }]
        }
    }]
    "items" to arr[
        backPack.items.map { item ->
            obj {
                "name" to item.name
                "description" to item.description
            }
    }]
}