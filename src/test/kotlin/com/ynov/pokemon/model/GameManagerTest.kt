package com.ynov.pokemon.model

import com.lectra.koson.arr
import com.lectra.koson.obj
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GameManagerTest : WithAssertions {
    private val attackEclair = Attack("éclair", 70)
    private val pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair))
    private val raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair))
    private val ronflex = Pokemon("ronflex", 70, 100, "normal", emptyList())
    private val psykokwak = Pokemon("psykokwak", 70, 100, "eau", emptyList())
    private val backPack = BackPack(listOf(raichu, pikachu), mutableListOf())
    private val aIbackPack = BackPack(listOf(psykokwak, ronflex), mutableListOf())
    private val player = Trainer("Sacha", pikachu, backPack)
    private val ai = Trainer("SachaAi", psykokwak, aIbackPack)

    @Test
    fun `should return current state of a game`() {
        val gameManager = GameManager(player, ai)
        val gameStateExpected = obj {
            "player1" to player.toJson()
            "player2" to ai.toJson()
        }.toString()
        assertEquals(gameStateExpected, gameManager.getGameState())
    }

    @Test
    fun `should return a winner when a player have all pokemons ko`(){
        val gameManager = GameManager(player, ai)
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        gameManager.refreshPlayersPokemonState()
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        assertEquals(player, gameManager.getWinner())
    }

    @Test
    fun `should return a null when no player have all pokemons ko`(){
        val gameManager = GameManager(player, ai)
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        gameManager.refreshPlayersPokemonState()
        assertEquals(null, gameManager.getWinner())
    }

    @Test
    fun `should authorize switch pokemon's ko only if all trainer's pokemons are not ko`(){
        val gameManager = GameManager(player, ai)
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        gameManager.refreshPlayersPokemonState()
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        gameManager.refreshPlayersPokemonState()
        assertEquals(player, gameManager.getWinner())
    }

}