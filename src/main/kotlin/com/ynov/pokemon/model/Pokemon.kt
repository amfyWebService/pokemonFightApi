package com.ynov.pokemon.model

data class Pokemon(val name : String, val currentHealthPoint : Int, val maxHealthPoint : Int , val type : String, val attacks: List<Attack>) {
    fun attack(pokemon : Pokemon, attackType: Attack) : Pokemon{
        return Pokemon("", 0, 0, "", emptyList())
    }

    fun applyDamage(damage : Int){

    }

    fun isKo() : Boolean{
        return true
    }
}
