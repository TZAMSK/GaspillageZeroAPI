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

    @GetMapping("/commandes/utilisateur/{idUtilisateur}")
    fun obtenirCommandesParUtilisateur(@PathVariable idUtilisateur: Int) = service.chercherCommandesParUtilisateur(idUtilisateur)

    @GetMapping("/commandes/utilisateur/{idUtilisateur}/commande/{idCommande}")
    fun obtenirCommandeParUtilisateur(@PathVariable idUtilisateur: Int, @PathVariable idCommande: Int) = service.chercherCommandeParUtilisateur(idUtilisateur, idCommande)

    @GetMapping("/commandes/épicerie/{idÉpicerie}")
    fun obtenirCommandesParÉpicerie(@PathVariable idÉpicerie: Int) = service.chercherCommandesParÉpicerie(idÉpicerie)

    @GetMapping("/commandes/épicerie/{idÉpicerie}/commande/{idCommande}")
    fun obtenirCommandeParÉpicerie(@PathVariable idÉpicerie: Int, @PathVariable idCommande: Int) = service.chercherCommandeParÉpicerie(idÉpicerie, idCommande)

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