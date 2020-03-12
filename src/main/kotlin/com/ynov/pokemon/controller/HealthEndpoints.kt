package com.ynov.pokemon.controller

import com.lectra.koson.obj
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthEndpoints {
    @GetMapping("/hello")
    fun helloWorld(): ResponseEntity<String> {
        return ResponseEntity.ok().body("hello world")
    }
}