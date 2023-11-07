package com.example.gaspillage_alimentaire_api.Controleurs

import org.springframework.web.bind.annotation.*
import com.example.gaspillage_alimentaire_api.Modèle.Livraison
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("\${api.base-path:}")
class LivraisonControleur {

    @GetMapping("/evaluations")
    fun obtenirEvaluations() {
        TODO("Méthode non-implémentée")
    }

    @GetMapping("/evaluations/{code}")
    fun obtenirEvaluationParCode(@PathVariable code: String) {
        TODO("Méthode non-implémentée")
    }

    @GetMapping("/livraisons")
    fun obtenirLivraisons() {
        TODO("Méthode non-implémentée")
    }

    @GetMapping("/livraisons/{code}")
    fun obtenirLivraisonParCode(@PathVariable code: String) {
        TODO("Méthode non-implémentée")
    }

    @PostMapping("/livraisons")
    fun inscrireLivraison(@RequestBody livraison: Livraison) {
        TODO("Méthode non-implémentée")
    }

    @PutMapping("/livraisons/{code}")
    fun majLivraison(@PathVariable code: String, @RequestBody livraison: Livraison) {
        TODO("Méthode non-implémentée")
    }

    @DeleteMapping("/livraisons/{code}")
    fun supprimerLivraison(@PathVariable code: String){
        TODO("Méthode non-implémentée")
    }

}