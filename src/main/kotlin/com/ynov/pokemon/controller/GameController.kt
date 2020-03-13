package com.ynov.pokemon.controller

import com.lectra.koson.obj
import com.ynov.pokemon.model.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {
    private lateinit var gameManager: GameManager

    @PostMapping("/games")
    fun startFight(): ResponseEntity<String> {
        val attackEclair = Attack("eclair", 70)
        val pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair))
        val ronflex = Pokemon("ronflex", 70, 100, "normal", emptyList())
        val psykokwak = Pokemon("psykokwak", 70, 100, "eau", emptyList())
        val backPack = BackPack(listOf(raichu, pikachu), mutableListOf())
        val aIbackPack = BackPack(listOf(psykokwak, ronflex), mutableListOf())
        val player = Trainer("Sacha", pikachu, backPack)
        val ai = Trainer("SachaAi", psykokwak, aIbackPack)
        this.gameManager = GameManager(player, ai)

        return this.getGameState()
    }

    @GetMapping("/games", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getGameState(): ResponseEntity<String>{
        return if (::gameManager.isInitialized)
            ResponseEntity.ok().body(gameManager.getGameState())
        else
            ResponseEntity.badRequest().body(obj { "message" to "You should start fight before" }.toString())
    }
}