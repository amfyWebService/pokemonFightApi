package com.ynov.pokemon.model

data class Trainer(val name : String, val currentPokemon : Pokemon, val backPack : BackPack) {
    fun useItem(item : Item, pokemon : Pokemon): Pokemon{
        return Pokemon("", 0, 0, "", emptyList())

    }

    fun pickUpPokemon(pokemon : Pokemon){

    }
}