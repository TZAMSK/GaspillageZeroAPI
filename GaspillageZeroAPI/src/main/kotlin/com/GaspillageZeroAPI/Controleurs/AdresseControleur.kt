package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Services.AdresseService
import org.springframework.web.bind.annotation.*

@RestController
class AdresseControleur(val service: AdresseService) {

    @GetMapping("/adresses")
    fun obtenirAdresses() = service.chercherTous()

    @GetMapping("/adresse/{idAdresse}")
    fun obtenirAdresseparCode(@PathVariable idAdresse: Int) = service.chercherParCode(idAdresse)

    @GetMapping("/utilisateur/{idUtilisateur}/adresse")
    fun obtenirAdresseUtilisateur(@PathVariable idUtilisateur: Int) = service.chercherParUtiliateur(idUtilisateur)

    @PostMapping("/adresse")
    fun ajouterAdresse(@RequestBody adresse: Adresse) {
        service.ajouter(adresse)
    }

    @DeleteMapping("/adresse/delete/{idAdresse}")
    fun suppimerAdresse(@PathVariable idAdresse: Int) {
        service.supprimer(idAdresse)
    }

    @PutMapping("/adresse/save")
    fun modifierAdresse(@PathVariable idAdresse: Int, @RequestBody adresse: Adresse){
        service.modifier(idAdresse, adresse)
    }
}