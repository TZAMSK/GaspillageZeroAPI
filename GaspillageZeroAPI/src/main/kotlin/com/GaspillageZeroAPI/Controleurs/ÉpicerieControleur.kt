package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ÉpicerieService
import org.springframework.web.bind.annotation.*

@RestController
class ÉpicerieControleur(val service: ÉpicerieService) {

    // MÉTHODES

    // MÉTHODE AFFICHAGES - LISTE ÉPICERIES
    @GetMapping("/épiceries")
    fun obtenirÉpiceries() = service.chercherTous()

    // MÉTHODE AFFICHAGES 2 - ÉPICERIE PAR CODE
    @GetMapping("/épicerie/{idÉpicerie}")
    fun obtenirÉpicerieparCode(@PathVariable idÉpicerie: Int) = service.chercherParCode(idÉpicerie)



    // MÉTHODES QUI VONT PROBABLEMENT PAS ETRE UTILISÉ (AJOUTER, SUPPRIMER, MODIFIER)

    //@PostMapping("/épicerie")
    //fun ajouterÉpicerie(@RequestBody épicerie: Épicerie) {
    //    service.ajouter(épicerie)
    //}

    //@DeleteMapping("/épicerie/delete/{idÉpicerie}")
    //fun suppimerÉpicerie(@PathVariable idÉpicerie: Int) {
    //    service.supprimer(idÉpicerie)
    //}

    //@PutMapping("/épicerie/save")
    //fun modifierÉpicerie(@PathVariable idÉpicerie: Int, @RequestBody épicerie: Épicerie){
    //    service.modifier(idÉpicerie, épicerie)
    //}
}