package com.ynov.pokemon.model

import kotlin.math.min

class Potion(id: String? = null) : Item("Potion", "Raise Health of 20", id) {
    override fun apply(pokemon: Pokemon): Pokemon {
        pokemon.currentHealthPoint = min(pokemon.currentHealthPoint + 20, pokemon.maxHealthPoint)
        return pokemon
    }
}