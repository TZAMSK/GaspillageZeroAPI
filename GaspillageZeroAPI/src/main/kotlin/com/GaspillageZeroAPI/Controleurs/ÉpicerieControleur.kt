package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ÉpicerieService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ÉpicerieControleur(val service: ÉpicerieService) {

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "épiceries trouvées"),
        ApiResponse(responseCode = "404", description = "épiceries non trouvées")
    ])
    @Operation(summary = "Obtenir la liste des épiceries")
    @GetMapping("/épiceries")
    fun obtenirÉpiceries(): ResponseEntity<List<Épicerie>> {
        val épiceries = service.chercherTous()
        return ResponseEntity.ok(épiceries)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "épicerie trouvée"),
        ApiResponse(responseCode = "404", description = "épicerie non trouvée")
    ])
    @Operation(summary = "Obtenir une épicerie par son code")
    @GetMapping("/épicerie/{idÉpicerie}")
    fun obtenirÉpicerieparCode(@PathVariable idÉpicerie: Int): ResponseEntity<Épicerie> {
        val épicerie = service.chercherParCode(idÉpicerie)
            ?: throw ExceptionRessourceIntrouvable("L'épicerie de code $idÉpicerie est introuvable")
        return ResponseEntity.ok(épicerie)
    }

    // Les méthodes pour ajouter, supprimer, et modifier n'ont pas besoin d'être implémentées.
}
