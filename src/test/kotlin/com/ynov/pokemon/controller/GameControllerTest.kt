package com.ynov.pokemon.controller

import com.lectra.koson.arr
import com.lectra.koson.obj
import com.ynov.pokemon.model.*
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
    private val backPack = BackPack(listOf(raichu, pikachu), mutableListOf())
    private val aIbackPack = BackPack(listOf(psykokwak, ronflex), mutableListOf())
    private val player = Trainer("Sacha", pikachu, backPack)
    private val ai = Trainer("SachaAi", psykokwak, aIbackPack)

    @Test
    fun `should return 200 when the gameManager started and return the game state`(){
        val gameStateExpected = obj {
            "player1" to player.toJson()
            "player2" to ai.toJson()
        }.toString()

        mvc?.perform(MockMvcRequestBuilders.post("/game")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andExpect(MockMvcResultMatchers.content()
                        .string(CoreMatchers.equalTo(gameStateExpected)))
    }

    @Test
    fun `should return 400 when the gameManager isn't started`(){
        mvc?.perform(MockMvcRequestBuilders.get("/game")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should return 200 when get game state is success`() {
        val gameStateExpected = obj {
            "player1" to player.toJson()
            "player2" to ai.toJson()
        }.toString()

        mvc?.perform(MockMvcRequestBuilders.get("/game")
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andExpect(MockMvcResultMatchers.content()
                .string(CoreMatchers.equalTo(gameStateExpected)))
    }

    @Test
    fun `should return 200 when make en action is success`() {
        mvc?.perform(MockMvcRequestBuilders.post("/game"))

        mvc?.perform(MockMvcRequestBuilders.post("/game/action").content( obj { "attackName" to "eclair" }.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should return 400 when make en action is not success`() {
        mvc?.perform(MockMvcRequestBuilders.post("/game"))

        mvc?.perform(MockMvcRequestBuilders.post("/game/action").content( obj { "attackName" to "coup de queue" }.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                ?.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}