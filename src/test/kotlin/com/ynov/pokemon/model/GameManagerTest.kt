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
    private lateinit var potion1: Potion
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
        // Attacks
        this.attackEclair = Attack("éclair", 70)
        // Pokemons
        this.pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair), id = "pika")
        this.raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair), id = "rai")
        this.ronflex = Pokemon("ronflex", 70, 100, "normal", emptyList(), id = "ron")
        this.psykokwak = Pokemon("psykokwak", 70, 100, "eau", emptyList(), id = "psyko")
        // Items
        this.potion1 = Potion(id = "popo")
        // Trainer
        this.backPack = BackPack(listOf(raichu, pikachu), mutableListOf(potion1))
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
        val actionRet = gameManager.action(player, attackId = "éclair")

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
            gameManager.action(thirdPlayer, attackId = "éclair")
        }

        print(error.message)
        assertEquals("You shall not pass \uD83E\uDDD9", error.message)
    }

    @Test
    fun `should throw IllegalState if the attack doesn't exist on current pokemon`() {
        val gameManager = GameManager(player, ai)

        val error = Assertions.assertThrows(IllegalStateException::class.java){
            gameManager.action(player, attackId = "ultralaser")
        }

        assertEquals("Attack not found", error.message)
    }

    @Test
    fun `should use potion on a own pokemon`() {
        val gameManager = GameManager(player, ai)

        gameManager.action(player, pokemonId = "rai", itemId = "popo")

        assertEquals(90, raichu.currentHealthPoint)
    }

    @Test
    fun `should throw IllegalState if try to use a potion that doesn't exist or not owned on a own pokemon`() {
        val gameManager = GameManager(player, ai)

        val error = Assertions.assertThrows(IllegalStateException::class.java){
            gameManager.action(player, pokemonId = "rai", itemId = "hyperPopo")
        }

        assertEquals("Item not found", error.message)
    }

    @Test
    fun `should throw IllegalState if try to use a potion on a pokemon not owned`() {
        val gameManager = GameManager(player, ai)

        val error = Assertions.assertThrows(IllegalStateException::class.java){
            gameManager.action(player, pokemonId = "draco", itemId = "popo")
        }

        assertEquals("Pokemon not found", error.message)
    }

    @Test
    fun `should switch pokemon`() {
        val gameManager = GameManager(player, ai)

        gameManager.action(player, pickUpId = "rai")

        assertEquals(raichu, player.currentPokemon)
    }

    @Test
    fun `should throw illegalState if pokemon doesnt exist on switch`() {
        val gameManager = GameManager(player, ai)

        val error = Assertions.assertThrows(IllegalStateException::class.java){
            gameManager.action(player, pickUpId = "draco")
        }

        assertEquals("Pokemon not found", error.message)
    }

}