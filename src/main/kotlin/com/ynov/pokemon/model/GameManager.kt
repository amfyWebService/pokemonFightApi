package com.ynov.pokemon.model

import com.lectra.koson.arr
import com.lectra.koson.obj

class GameManager(private val player1: Trainer, private val player2: Trainer) {
    fun getGameState(): String {
        return obj {
            "player1" to player1.toJson()
            "player2" to player2.toJson()
        }.toString()
    }

    fun refreshPlayersPokemonState() {
        if (!player1.isAllPokemonsKo()) player1.switchKoPokemon()
        if (!player2.isAllPokemonsKo()) player2.switchKoPokemon()
    }

    fun getWinner(): Trainer? {
        return when {
            player1.isAllPokemonsKo() -> player2
            player2.isAllPokemonsKo() -> player1
            else -> null
        }
    }

    fun action(player: Trainer, pokemon: String? = null, item: String? = null, attack: String? = null, pickUp: String? = null): String {
        if(this.getWinner() != null)
            throw IllegalStateException("Game over")

        val opponentPlayer = if (player == this.player1) this.player2 else this.player1

        player.currentPokemon?.let { pokemonAttacker ->
            val attackIndex = pokemonAttacker.attacks.indexOfFirst { it.name == attack }
            val attackObj = pokemonAttacker.attacks[attackIndex]
            opponentPlayer.currentPokemon?.let { pokemonAttacked ->
                pokemonAttacker.attack(pokemonAttacked, attackObj)
                this.refreshPlayersPokemonState()
            } ?: throw IllegalStateException("The opponent player haven't a selected pokemon")
        } ?: throw IllegalStateException("The player haven't a selected pokemon")

        return this.getGameState()
    }
}

fun Trainer.toJson() = obj {
    "name" to name
    "currentPokemon" to currentPokemon?.toJson()
    "pokemons" to arr[backPack.pokemons.map { pokemon -> pokemon.toJson() }]
    "items" to arr[
            backPack.items.map { item ->
                obj {
                    "id" to item.id
                    "name" to item.name
                    "description" to item.description
                }
            }]
}

fun Trainer.isAllPokemonsKo(): Boolean {
    val koPokemons = backPack.pokemons.filter { it.isKo() }
    return backPack.pokemons.size == koPokemons.size
}

fun Trainer.switchKoPokemon() {
    currentPokemon?.run {
        if (this.isKo()) {
            currentPokemon = backPack.pokemons.filter { !it.isKo() }.random()
        }
    }
}

fun Pokemon.toJson() = obj {
    "id" to id
    "name" to name
    "type" to type
    "currentHealthPoint" to currentHealthPoint
    "maxHealthPoint" to maxHealthPoint
    "attacks" to arr[attacks.map { attack ->
        obj {
            "name" to attack.name
            "damage" to attack.damage
        }
    }]
}