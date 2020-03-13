package com.ynov.pokemon.model

import java.util.*

abstract class Item(val name : String, val description : String, val id: String? = UUID.randomUUID().toString()) {
    abstract fun apply(pokemon : Pokemon): Pokemon
}
