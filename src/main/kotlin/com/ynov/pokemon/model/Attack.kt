package com.ynov.pokemon.model

data class Attack(val name : String, val damage : Int) {
    fun apply(pokemon : Pokemon) : Pokemon{
        return Pokemon("", 0, 0, "", emptyList())

    }
}
