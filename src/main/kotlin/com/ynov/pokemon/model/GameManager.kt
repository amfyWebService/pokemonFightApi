package com.ynov.pokemon.model

import com.lectra.koson.arr
import com.lectra.koson.obj

class GameManager(private val player1: Trainer, private val player2: Trainer) {
    fun getGameState(): String {
        player1.switchKoPokemon()
        player2.switchKoPokemon()
        return obj {
            "player1" to player1.toJson()
            "player2" to player2.toJson()
        }.toString()
    }

    fun getWinner(): Trainer? {
        return when {
            player1.isAllPokemonsKo() -> player2
            player2.isAllPokemonsKo() -> player1
            else -> null
        }
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

fun Trainer.isAllPokemonsKo(): Boolean {
    val koPokemons = backPack.pokemons.filter { it.isKo() }
    return backPack.pokemons.size == koPokemons.size
}

fun Trainer.switchKoPokemon(){
    currentPokemon?.run {
        if (this.isKo()){
            currentPokemon = backPack.pokemons.filter { !it.isKo() }.random()
        }
    }
}