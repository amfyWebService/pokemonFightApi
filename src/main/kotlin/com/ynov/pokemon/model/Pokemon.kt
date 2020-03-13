package com.ynov.pokemon.model

import java.util.*

data class Pokemon(
        val name: String,
        var currentHealthPoint: Int,
        val maxHealthPoint: Int,
        val type: String,
        val attacks: List<Attack>,
        val id: String? = UUID.randomUUID().toString()
) {
    fun attack(pokemon: Pokemon, attackType: Attack): Pokemon {
        pokemon.applyDamage(attackType.damage)
        return pokemon
    }

    fun applyDamage(damage: Int) {
        this.currentHealthPoint -= damage
    }

    fun isKo() = this.currentHealthPoint <= 0
}
