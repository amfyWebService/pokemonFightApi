package com.ynov.pokemon.controller

import com.lectra.koson.arr
import com.lectra.koson.obj
import com.ynov.pokemon.model.Attack
import com.ynov.pokemon.model.BackPack
import com.ynov.pokemon.model.Pokemon
import com.ynov.pokemon.model.Trainer
import org.assertj.core.api.WithAssertions
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
internal class GameControllerTest: WithAssertions {
    @Autowired
    private val mvc: MockMvc? = null
    private val attackEclair = Attack("eclair", 70)
    private val pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair))
    private val raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair))
    private val ronflex = Pokemon("ronflex", 70, 100, "normal", emptyList())
    private val psykokwak = Pokemon("psykokwak", 70, 100, "eau", emptyList())
    private val backPack = BackPack(listOf(raichu, pikachu), emptyList())
    private val aIbackPack = BackPack(listOf(psykokwak, ronflex), emptyList())
    private val player = Trainer("Sacha", pikachu, backPack)
    private val ai = Trainer("SachaAi", psykokwak, aIbackPack)
    @Test
    fun `should return 200 when get game state is success`() {
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
        mvc?.let {
            it.perform(MockMvcRequestBuilders.get("/games").accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk)
                    .andExpect(MockMvcResultMatchers.content()
                            .string(CoreMatchers.equalTo(gameStateExpected)))
        }
    }
}