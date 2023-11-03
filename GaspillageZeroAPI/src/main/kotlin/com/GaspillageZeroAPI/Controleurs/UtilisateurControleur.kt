package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Services.UtilisateurService
import org.springframework.web.bind.annotation.*

@RestController
class UtilisateurControleur(val service: UtilisateurService) {

    @GetMapping("/utilisateurs")
    fun obtenirUtilisateurs() = service.chercherTous()

    @GetMapping("/utilisateur/{idUtilisateur}")
    fun obtenirUtilisateurparCode(@PathVariable idÉpicerie: Int) = service.chercherParCode(idÉpicerie)

    @PostMapping("/utilisateur")
    fun ajouterUtilisateur(@RequestBody utilisateur: Utilisateur) {
        service.ajouter(utilisateur)
    }

    @DeleteMapping("/utilisateur/delete/{idUtilisateur}")
    fun suppimerUtilisateur(@PathVariable idUtilisateur: Int) {
        service.supprimer(idUtilisateur)
    }

    @PutMapping("/utilisateur/save")
    fun modifierUtilisateur(@PathVariable idUtilisateur: Int, @RequestBody utilisateur: Utilisateur){
        service.modifier(idUtilisateur, utilisateur)
    }
}