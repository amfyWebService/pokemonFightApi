package com.ynov.pokemon.model

data class Pokemon(val name : String, val currentHealthPoint : Int, val maxHealthPoint : Int , val type : String, val attacks: List<Attack>) {
    fun attack(pokemon : Pokemon) : Pokemon{

    }

    fun applyDamage(damage : Int){

    }

    fun isKo() : Boolean{

    }
}
