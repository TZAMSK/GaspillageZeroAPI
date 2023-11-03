package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Services.CommandeService
import org.springframework.web.bind.annotation.*

@RestController
class CommandeControleur(val service: CommandeService) {

    @GetMapping("/commandes")
    fun obtenirCommandes() = service.chercherTous()

    @GetMapping("/commande/{idCommande}")
    fun obtenirCommandeparCode(@PathVariable idCommande: Int) = service.chercherParCode(idCommande)

    @PostMapping("/commande")
    fun ajouterCommande(@RequestBody commande: Commande) {
        service.ajouter(commande)
    }

    @DeleteMapping("/commande/delete/{idCommande}")
    fun suppimerCommande(@PathVariable idCommande: Int) {
        service.supprimer(idCommande)
    }

    @PutMapping("/commande/save")
    fun modifierCommande(@PathVariable idCommande: Int, @RequestBody commande: Commande){
        service.modifier(idCommande, commande)
    }
}