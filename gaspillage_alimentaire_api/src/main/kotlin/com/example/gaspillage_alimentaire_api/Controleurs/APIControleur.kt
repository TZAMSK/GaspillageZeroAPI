package com.example.gaspillage_alimentaire_api.Controleurs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class APIControleur {

    @GetMapping("/")
    fun index() = "Api web du service de livraison"
}