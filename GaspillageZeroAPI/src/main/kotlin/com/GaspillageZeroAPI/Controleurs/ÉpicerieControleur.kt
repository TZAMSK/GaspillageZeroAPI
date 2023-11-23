package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ÉpicerieIntrouvableException
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ÉpicerieService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
class ÉpicerieControleur(val service: ÉpicerieService) {

    // MÉTHODES

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Épiceries trouvées"),
        ApiResponse(responseCode = "404", description = "Épiceries non trouvées")
    ])

    // MÉTHODE AFFICHAGES - LISTE ÉPICERIES
    @Operation(summary = "Obtenir la liste de toutes les épiceries")
    @GetMapping("/épiceries")
    fun obtenirÉpiceries() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Épicerie trouvée"),
        ApiResponse(responseCode = "404", description = "Épicerie non trouvée")
    ])

    // MÉTHODE AFFICHAGES 2 - ÉPICERIE PAR CODE
    @Operation(summary = "Obtenir l'épicerie par le ID de celui-ci")
    @GetMapping("/épicerie/{idÉpicerie}")
    fun obtenirÉpicerieparCode(@PathVariable idÉpicerie: Int): Épicerie? {
        val épicerie = service.chercherParCode(idÉpicerie)
        if(épicerie == null){
            throw ÉpicerieIntrouvableException("L'épicerie de code $idÉpicerie est introuvable")
        }
        return épicerie
    }

    // MÉTHODES QUI VONT PROBABLEMENT PAS ETRE UTILISÉ (AJOUTER, SUPPRIMER, MODIFIER)

    //@PostMapping("/épicerie")
    //fun ajouterÉpicerie(@RequestBody épicerie: Épicerie) {
    //    service.ajouter(épicerie)
    //}

    // MÉTHODE POUR TESTER L'EXCEPTION
    //@Operation(summary = "Permet de retirer une épicerie de la base de données")
    //@DeleteMapping("/épicerie/{idÉpicerie}")
    //fun suppimerÉpicerie(@PathVariable idÉpicerie: Int) {
    //    service.supprimer(idÉpicerie)
    //}

    //@PutMapping("/épicerie/save")
    //fun modifierÉpicerie(@PathVariable idÉpicerie: Int, @RequestBody épicerie: Épicerie){
    //    service.modifier(idÉpicerie, épicerie)
    //}
}