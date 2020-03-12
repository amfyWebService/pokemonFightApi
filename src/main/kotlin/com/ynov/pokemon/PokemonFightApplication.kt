package com.ynov.pokemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class PokemonFightApplication

fun main(args: Array<String>) {
    runApplication<PokemonFightApplication>(*args)
}