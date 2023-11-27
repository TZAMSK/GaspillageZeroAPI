package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRequeteInvalide
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Services.GabaritProduitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class GabaritProduitController(val service: GabaritProduitService) {

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduits trouvés"),
        ApiResponse(responseCode = "404", description = "Gabaritproduits non trouvés"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Obtenir la liste des gabaritproduits")
    @GetMapping("/gabaritproduits")
    fun obtenirGabaritProduits(): ResponseEntity<List<GabaritProduit>> {
        try {
            val gabarits = service.chercherTous()
            if (gabarits.isNotEmpty()) {
                return ResponseEntity.ok(gabarits)
            } else {
                throw ExceptionRessourceIntrouvable("Gabaritproduits non trouvés")
            }
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur", e)
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduit trouvé"),
        ApiResponse(responseCode = "404", description = "Gabaritproduit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Obtenir un gabaritproduit par son ID")
    @GetMapping("/gabaritproduit/{idGabaritProduit}")
    fun obtenirGabaritProduitParCode(@PathVariable idGabaritProduit: Int): ResponseEntity<GabaritProduit> {
        return try {
            val gabarit = service.chercherParCode(idGabaritProduit)
            gabarit?.let { ResponseEntity.ok(it) }
                ?: throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: EmptyResultDataAccessException) {
            throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur lors de la recherche du gabaritproduit avec l'ID $idGabaritProduit", e)
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Gabaritproduit créé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées"),
        ApiResponse(responseCode = "409", description = "Conflit: gabaritproduit déjà existant ou violation de contrainte"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Ajouter un gabaritproduit")
    @PostMapping("/gabaritproduit")
    fun ajouterGabarit(@RequestBody gabaritProduit: GabaritProduit): ResponseEntity<Void> {
        return try {
            service.ajouter(gabaritProduit)
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (e: DataIntegrityViolationException) {
            throw ExceptionConflitRessourceExistante("Conflit: gabaritproduit déjà existant ou violation de contrainte")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide ou données mal formées")
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur lors de l'ajout du gabaritproduit")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduit supprimé"),
        ApiResponse(responseCode = "404", description = "Gabaritproduit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Supprimer un gabaritproduit par son ID")
    @DeleteMapping("/gabaritproduit/{idGabaritProduit}")
    fun supprimerGabarit(@PathVariable idGabaritProduit: Int): ResponseEntity<Void> {
        return try {
            if (service.supprimer(idGabaritProduit)) {
                ResponseEntity.ok().build()
            } else {
                throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
            }
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur lors de la suppression du gabaritproduit avec l'ID $idGabaritProduit")
        }
    }


    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduit modifié"),
        ApiResponse(responseCode = "404", description = "Gabaritproduit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées"),
        ApiResponse(responseCode = "409", description = "Conflit: Violation de contrainte"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Modifier un gabaritproduit par son ID")
    @PutMapping("/gabaritproduit/{idGabaritProduit}")
    fun modifierGabarit(@PathVariable idGabaritProduit: Int, @RequestBody gabaritProduit: GabaritProduit): ResponseEntity<Void> {
        return try {
            if (service.modifier(idGabaritProduit, gabaritProduit)) {
                ResponseEntity.ok().build()
            } else {
                throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
            }
        } catch (e: DataIntegrityViolationException) {
            throw ExceptionConflitRessourceExistante("Conflit: Violation de contrainte pour l'ID $idGabaritProduit")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide ou données mal formées : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur lors de la modification du gabaritproduit avec l'ID $idGabaritProduit")
        }
    }


}
