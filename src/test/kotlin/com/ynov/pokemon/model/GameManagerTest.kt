package com.ynov.pokemon.model

import com.lectra.koson.arr
import com.lectra.koson.obj
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GameManagerTest : WithAssertions {
    private val attackEclair = Attack("éclair", 30)
    private val pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair))
    private val raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair))
    private val backPack = BackPack(listOf(raichu, pikachu), emptyList())
    private val player = Trainer("Sacha", null, backPack)
    private val ai = Trainer("SachaAi", null, backPack)

    @Test
    fun `should return current state of a fight`() {
        val gameManager = GameManager(player, ai)
        val gameStateExpected = obj {
            "player1" to obj {
                "name" to player.name
                "pokemons" to arr[player.backPack.pokemons.map {
                    obj {
                        "name" to it.name
                        "type" to it.type
                        "currentHealthPoint" to it.currentHealthPoint
                        "maxHealthPoint" to it.maxHealthPoint
                        "attacks" to arr[it.attacks.map { attack ->
                            obj {
                                "name" to attack.name
                                "damage" to attack.damage
                            }
                        }]
                    }
                }]
                "items" to arr[
                        player.backPack.items.map { item ->
                            obj {
                                "name" to item.name
                                "description" to item.description
                            }
                        }]

            }
            "player2" to obj {
                "name" to ai.name
                "pokemons" to arr[ai.backPack.pokemons.map {
                    obj {
                        "name" to it.name
                        "type" to it.type
                        "currentHealthPoint" to it.currentHealthPoint
                        "maxHealthPoint" to it.maxHealthPoint
                        "attacks" to arr[it.attacks.map { attack ->
                            obj {
                                "name" to attack.name
                                "damage" to attack.damage
                            }
                        }]
                    }
                }]
                "items" to arr[
                        ai.backPack.items.map { item ->
                            obj {
                                "name" to item.name
                                "description" to item.description
                            }
                        }]
            }
        }.toString()
        assertEquals(gameStateExpected, gameManager.gameState())
    }
}