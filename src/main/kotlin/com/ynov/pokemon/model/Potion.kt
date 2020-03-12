package com.ynov.pokemon.model

import kotlin.math.min

class Potion : Item("Potion", "Raise Health of 20") {
    override fun apply(pokemon: Pokemon): Pokemon {
        pokemon.currentHealthPoint += 20
        pokemon.currentHealthPoint = min(pokemon.currentHealthPoint, pokemon.maxHealthPoint)
        return pokemon
    }
}