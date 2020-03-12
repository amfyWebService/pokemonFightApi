package com.ynov.pokemon.model

data class Pokemon(val name : String, var currentHealthPoint : Int, val maxHealthPoint : Int , val type : String, val attacks: List<Attack>) {
    fun attack(pokemon : Pokemon, attackType: Attack) : Pokemon{
        pokemon.applyDamage(attackType.damage)
        return pokemon
    }

    fun applyDamage(damage : Int){
        this.currentHealthPoint -= damage
    }

    fun isKo() : Boolean{
        return this.currentHealthPoint <= 0
    }
}
