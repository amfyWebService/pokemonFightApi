package com.ynov.pokemon.model

class GameManager(val player: Trainer, val ai: Trainer){
    fun gameState(): String {
        return ""
    }

    private fun isAllPokemonsKo(trainer: Trainer): Boolean {
        return false
    }
}