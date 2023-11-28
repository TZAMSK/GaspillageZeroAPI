package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.*
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Services.ProduitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProduitControleur(val service: ProduitService) {

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produits trouvés"),
        ApiResponse(responseCode = "404", description = "Produits non trouvés"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Obtenir la liste des produits")
    @GetMapping("/produits")
    fun obtenirProduits(): ResponseEntity<List<Produit>> {
        return try {
            val produits = service.chercherTous()
            if (produits.isNotEmpty()) {
                ResponseEntity.ok(produits)
            } else {
                throw ExceptionRessourceIntrouvable("Produits non trouvés")
            }
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur", e)
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produit trouvé"),
        ApiResponse(responseCode = "404", description = "Produit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées")
    ])
    @Operation(summary = "Obtenir un produit par son ID")
    @GetMapping("/produit/{idProduit}")
    fun obtenirProduitParCode(@PathVariable idProduit: Int): ResponseEntity<Produit> {
        return try {
            val produit = service.chercherParCode(idProduit)
                ?: throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit non trouvé")
            ResponseEntity.ok(produit)
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit non trouvé")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produits trouvés"),
        ApiResponse(responseCode = "404", description = "Produits non trouvés"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Obtenir la liste des produits avec le ID de l'épicerie")
    @GetMapping("/épicerie/{idÉpicerie}/produits")
    fun obtenirProduitÉpicerie(@PathVariable idÉpicerie: Int): ResponseEntity<List<Produit>> {
        return try {
            val produits = service.chercherParÉpicerie(idÉpicerie)
                ?: throw ExceptionRessourceIntrouvable("Produits de l'épicerie avec l'ID $idÉpicerie non trouvés")
            ResponseEntity.ok(produits)
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur", e)
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produit trouvé"),
        ApiResponse(responseCode = "404", description = "Produit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées")
    ])
    @Operation(summary = "Obtenir un produit avec le ID de l'épicerie et le ID du produit")
    @GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
    fun obtenirProduitÉpicerieParCode(@PathVariable idÉpicerie: Int, @PathVariable idProduit: Int): ResponseEntity<Produit> {
        return try {
            val produit = service.chercherParÉpicerieParCode(idÉpicerie, idProduit)
                ?: throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit et avec l'épicerie de ID $idÉpicerie non trouvé")
            ResponseEntity.ok(produit)
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit et avec l'épicerie de ID $idÉpicerie non trouvé")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Produit ajouté"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées"),
        ApiResponse(responseCode = "409", description = "Conflit: Violation de contrainte"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Ajouter un produit")
    @PostMapping("/produit")
    fun ajouterProduit(@RequestBody produit: Produit): ResponseEntity<Void> {
        return try {
            service.ajouter(produit)
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (e: DataIntegrityViolationException) {
            throw ExceptionConflitRessourceExistante("Conflit: Violation de contrainte")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide ou données mal formées")
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur lors de l'ajout du produit")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produit supprimé"),
        ApiResponse(responseCode = "404", description = "Produit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide")
    ])
    @Operation(summary = "Supprimer un produit par son ID")
    @DeleteMapping("/produit/{idProduit}")
    fun supprimerProduit(@PathVariable idProduit: Int): ResponseEntity<Void> {
        return try {
            if (service.supprimer(idProduit)) {
                ResponseEntity.ok().build()
            } else {
                throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit non trouvé")
            }
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit non trouvé")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produit modifié"),
        ApiResponse(responseCode = "404", description = "Produit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées"),
        ApiResponse(responseCode = "409", description = "Conflit: Violation de contrainte")
    ])
    @Operation(summary = "Modifier un produit par son ID")
    @PutMapping("/produit/{idProduit}")
    fun modifierProduit(@PathVariable idProduit: Int, @RequestBody produit: Produit): ResponseEntity<Void> {
        return try {
            if (service.modifier(idProduit, produit)) {
                ResponseEntity.ok().build()
            } else {
                throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit non trouvé")
            }
        } catch (e: DataIntegrityViolationException) {
            throw ExceptionConflitRessourceExistante("Conflit: Violation de contrainte pour le ID $idProduit")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide ou données mal formées : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("Produit avec l'ID $idProduit non trouvé")
        }
    }
}
