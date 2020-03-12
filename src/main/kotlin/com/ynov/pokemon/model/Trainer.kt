package com.ynov.pokemon.model

data class Trainer(val name : String, var currentPokemon : Pokemon?, val backPack : BackPack) {
    init {
        if (currentPokemon == null) {
            currentPokemon = backPack.pokemons.random()
        }

    }

    fun useItem(item : Item, pokemon : Pokemon): Pokemon{
        return Pokemon("", 0, 0, "", emptyList())

    }

    fun pickUpPokemon(pokemon : Pokemon){

    }
}