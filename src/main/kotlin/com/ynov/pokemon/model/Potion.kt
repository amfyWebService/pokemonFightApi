package com.ynov.pokemon.model

class Potion : Item("Potion", "Raise Health of 20") {
    override fun apply(pokemon: Pokemon): Pokemon {
        pokemon.currentHealthPoint += 20
        return pokemon
    }
}