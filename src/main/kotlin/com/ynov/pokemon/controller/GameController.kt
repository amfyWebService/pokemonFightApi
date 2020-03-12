package com.ynov.pokemon.controller

import com.ynov.pokemon.model.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController {
    private val attackEclair = Attack("eclair", 70)
    private val pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair))
    private val raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair))
    private val ronflex = Pokemon("ronflex", 70, 100, "normal", emptyList())
    private val psykokwak = Pokemon("psykokwak", 70, 100, "eau", emptyList())
    private val backPack = BackPack(listOf(raichu, pikachu), emptyList())
    private val aIbackPack = BackPack(listOf(psykokwak, ronflex), emptyList())
    private val player = Trainer("Sacha", pikachu, backPack)
    private val ai = Trainer("SachaAi", psykokwak, aIbackPack)
    private val gameManager = GameManager(player, ai)

    @GetMapping("/games", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getGameState(): ResponseEntity<String>{
        return ResponseEntity.ok().body(gameManager.getGameState())
    }
}