package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Panier
import com.GaspillageZeroAPI.Services.PanierService
import org.springframework.web.bind.annotation.*

@RestController
class PanierControleur(val service: PanierService) {

    @GetMapping("/paniers")
    fun obtenirPaniers() = service.chercherTous()

    @GetMapping("/panier/{idPanier}")
    fun obtenirPanierparCode(@PathVariable idPanier: Int) = service.chercherParCode(idPanier)

    @PostMapping("/panier")
    fun ajouterPanier(@RequestBody panier: Panier) {
        service.ajouter(panier)
    }

    @DeleteMapping("/panier/delete/{idPanier}")
    fun suppimerPanier(@PathVariable idPanier: Int) {
        service.supprimer(idPanier)
    }

    @PutMapping("/panier/save")
    fun modifierPanier(@PathVariable idPanier: Int, @RequestBody panier: Panier){
        service.modifier(idPanier, panier)
    }
}