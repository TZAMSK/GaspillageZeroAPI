package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRequeteInvalide
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ÉpicerieService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ÉpicerieControleur(val service: ÉpicerieService) {

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Épiceries trouvées"),
        ApiResponse(responseCode = "404", description = "Épiceries non trouvées"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Obtenir la liste des épiceries")
    @GetMapping("/épiceries")
    fun obtenirÉpiceries(): ResponseEntity<List<Épicerie>> {
        return try {
            val épiceries = service.chercherTous()
            if (épiceries.isNotEmpty()) {
                ResponseEntity.ok(épiceries)
            } else {
                throw ExceptionRessourceIntrouvable("Épiceries non trouvées")
            }
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur", e)
        }
    }
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Épicerie trouvée"),
        ApiResponse(responseCode = "404", description = "Épicerie non trouvée"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Obtenir une épicerie par son ID")
    @GetMapping("/épicerie/{idÉpicerie}")
    fun obtenirÉpicerieparCode(@PathVariable idÉpicerie: Int): ResponseEntity<Épicerie> {
        return try {
            val épicerie = service.chercherParCode(idÉpicerie)
                ?: throw ExceptionRessourceIntrouvable("L'épicerie avec l'ID $idÉpicerie est introuvable")
            ResponseEntity.ok(épicerie)
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("L'épicerie avec l'ID $idÉpicerie est introuvable")
        }
    }
}
