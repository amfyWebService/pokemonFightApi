package com.ynov.pokemon.model

import com.lectra.koson.arr
import com.lectra.koson.obj
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNotEquals

internal class GameManagerTest : WithAssertions {
    private lateinit var attackEclair: Attack
    private lateinit var pikachu: Pokemon
    private lateinit var raichu: Pokemon
    private lateinit var ronflex: Pokemon
    private lateinit var psykokwak: Pokemon
    private lateinit var backPack: BackPack
    private lateinit var aIbackPack: BackPack
    private lateinit var player: Trainer
    private lateinit var ai: Trainer

    @BeforeEach
    fun setup() {
        this.attackEclair = Attack("éclair", 70)
        this.pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair))
        this.raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair))
        this.ronflex = Pokemon("ronflex", 70, 100, "normal", emptyList())
        this.psykokwak = Pokemon("psykokwak", 70, 100, "eau", emptyList())
        this.backPack = BackPack(listOf(raichu, pikachu), mutableListOf())
        this.aIbackPack = BackPack(listOf(psykokwak, ronflex), mutableListOf())
        this.player = Trainer("Sacha", pikachu, backPack)
        this.ai = Trainer("SachaAi", psykokwak, aIbackPack)
    }

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
    fun `should return a winner when a player have all pokemons ko`() {
        val gameManager = GameManager(player, ai)
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        gameManager.refreshPlayersPokemonState()
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        assertEquals(player, gameManager.getWinner())
    }

    @Test
    fun `should return a null when no player have all pokemons ko`() {
        val gameManager = GameManager(player, ai)
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        gameManager.refreshPlayersPokemonState()
        assertEquals(null, gameManager.getWinner())
    }

    @Test
    fun `should not authorize switch pokemon if all trainer's pokemons are ko`() {
        val gameManager = GameManager(player, ai)
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }
        gameManager.refreshPlayersPokemonState()
        ai.currentPokemon?.let { player.currentPokemon?.attack(it, player.currentPokemon!!.attacks.first()) }

        val oldPokemon = ai.currentPokemon
        gameManager.refreshPlayersPokemonState()
        assertEquals(true, ai.isAllPokemonsKo())
        assertEquals(oldPokemon, ai.currentPokemon)
        assertEquals(player, gameManager.getWinner())
    }

    @Test
    fun `should attack the opponent pokemon`() {
        val gameManager = GameManager(player, ai)

        val opponentPokemon = ai.currentPokemon
        val actionRet = gameManager.action(player, attack = "éclair")

        assertEquals(0, opponentPokemon?.currentHealthPoint)
        assertNotEquals(opponentPokemon, ai.currentPokemon)
        assertEquals(obj {
            "player1" to player.toJson()
            "player2" to ai.toJson()
        }.toString(), actionRet)
    }

    @Test
    fun `should throw IllegalState if the player who make the action is not include in the game`() {
        val gameManager = GameManager(player, ai)
        val thirdPlayer = Trainer("Corona", null, BackPack(listOf(pikachu), mutableListOf()))

        val error = Assertions.assertThrows(IllegalStateException::class.java){
            gameManager.action(thirdPlayer, attack = "éclair")
        }

        print(error.message)
        assertEquals("You shall not pass \uD83E\uDDD9", error.message)
    }

    @Test
    fun `should throw IllegalState if the attack doesn't exist on current pokemon`() {
        val gameManager = GameManager(player, ai)

        val error = Assertions.assertThrows(IllegalStateException::class.java){
            gameManager.action(player, attack = "ultralaser")
        }

        assertEquals("Attack not found", error.message)
    }

}