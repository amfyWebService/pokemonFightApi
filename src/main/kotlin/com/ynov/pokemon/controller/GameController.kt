package com.ynov.pokemon.controller

import com.lectra.koson.obj
import com.ynov.pokemon.model.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Null

@RestController
@RequestMapping("/game")
class GameController {
    private lateinit var player: Trainer
    private lateinit var ai: Trainer
    private lateinit var gameManager: GameManager

    @PostMapping()
    fun startFight(): ResponseEntity<String> {
        val attackEclair = Attack("eclair", 70)
        val pikachu = Pokemon("pikachu", 100, 100, "electric", listOf(attackEclair))
        val raichu = Pokemon("raichu", 70, 100, "electric", listOf(attackEclair))
        val ronflex = Pokemon("ronflex", 70, 100, "normal", emptyList())
        val psykokwak = Pokemon("psykokwak", 70, 100, "eau", emptyList())
        val backPack = BackPack(listOf(raichu, pikachu), mutableListOf())
        val aIbackPack = BackPack(listOf(psykokwak, ronflex), mutableListOf())
        player = Trainer("Sacha", pikachu, backPack)
        ai = Trainer("SachaAi", psykokwak, aIbackPack)
        this.gameManager = GameManager(player, ai)

        return this.getGameState()
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getGameState(): ResponseEntity<String>{
        return if (::gameManager.isInitialized)
            ResponseEntity.ok().body(gameManager.getGameState())
        else
            ResponseEntity.badRequest().body(obj { "message" to "You should start fight before" }.toString())
    }

    @PostMapping("/action")
    fun makeAction(@RequestBody body: ActionRequestBody): ResponseEntity<String> {
        return try {
            ResponseEntity.ok().body(this.gameManager.action(this.player, attackName = body.attackName, pokemonId = body.pokemonId, itemId = body.itemId, pickUpId = body.pickUpId))
        } catch (e: Exception){
            ResponseEntity.badRequest().body(obj { "message" to e.message }.toString())
        }
    }
}

class ActionRequestBody {
    @Nullable
    val pokemonId: String? = null
    @Nullable
    val attackName: String? = null
    @Nullable
    val pickUpId: String? = null
    @Nullable
    val itemId: String? = null
}