package com.ynov.pokemon.model

import java.util.*

data class Trainer(val name : String, val currentPokemon : Pokemon, val backPack : BackPack) {
    fun useItem(item : Item, pokemon : Pokemon): Pokemon{

    }

    fun pickUpPokemon(pokemon : Pokemon){

    }
}