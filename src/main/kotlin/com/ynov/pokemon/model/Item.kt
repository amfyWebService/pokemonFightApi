package com.ynov.pokemon.model

abstract class Item(val name : String, val description : String) {
    abstract fun apply(pokemon : Pokemon): Pokemon
}
