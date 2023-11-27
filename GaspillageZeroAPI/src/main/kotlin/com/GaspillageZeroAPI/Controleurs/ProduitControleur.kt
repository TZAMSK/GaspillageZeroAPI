package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Services.ProduitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProduitControleur(val service: ProduitService) {

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "produits trouvés"),
        ApiResponse(responseCode = "404", description = "produits non trouvés")
    ])
    @Operation(summary = "Obtenir la liste des produits")
    @GetMapping("/produits")
    fun obtenirProduits(): ResponseEntity<List<Produit>> {
        val produits = service.chercherTous()
        return ResponseEntity.ok(produits)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "produit trouvé"),
        ApiResponse(responseCode = "404", description = "produit non trouvé")
    ])
    @Operation(summary = "Obtenir un produit par son code")
    @GetMapping("/produit/{idProduit}")
    fun obtenirProduitParCode(@PathVariable idProduit: Int): ResponseEntity<Produit> {
        val produit = service.chercherParCode(idProduit)
            ?: throw ExceptionRessourceIntrouvable("Le produit de code $idProduit est introuvable")
        return ResponseEntity.ok(produit)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "produits trouvés"),
        ApiResponse(responseCode = "404", description = "produits non trouvés")
    ])
    @Operation(summary = "Obtenir la liste des produits avec le code de l'épicerie")
    @GetMapping("/épicerie/{idÉpicerie}/produits")
    fun obtenirProduitÉpicerie(@PathVariable idÉpicerie: Int): ResponseEntity<List<Produit>> {
        val produits = service.chercherParÉpicerie(idÉpicerie)
            ?: throw ExceptionRessourceIntrouvable("Les produits de l'épicerie de code $idÉpicerie sont introuvables")
        return ResponseEntity.ok(produits)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "produit trouvé"),
        ApiResponse(responseCode = "404", description = "produit non trouvé")
    ])
    @Operation(summary = "Obtenir un produit avec le code de l'épicerie et le code du produit")
    @GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
    fun obtenirProduitÉpicerieParCode(@PathVariable idÉpicerie: Int, @PathVariable idProduit: Int): ResponseEntity<Produit> {
        val produit = service.chercherParÉpicerieParCode(idÉpicerie, idProduit)
            ?: throw ExceptionRessourceIntrouvable("Le produit de code $idProduit avec épicerie code $idÉpicerie est introuvable")
        return ResponseEntity.ok(produit)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "produit trouvé"),
        ApiResponse(responseCode = "404", description = "produit non trouvé")
    ])
    @Operation(summary = "Ajouter un produit")
    @PostMapping("/produit")
    fun ajouterProduit(@RequestBody produit: Produit): ResponseEntity<Void> {
        service.ajouter(produit)
        return ResponseEntity.ok().build()
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "produit trouvé"),
        ApiResponse(responseCode = "404", description = "produit non trouvé")
    ])
    @Operation(summary = "Supprimer un produit par son code")
    @DeleteMapping("/produit/{idProduit}")
    fun supprimerProduit(@PathVariable idProduit: Int): ResponseEntity<Void> {
        service.supprimer(idProduit)
        return ResponseEntity.ok().build()
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "produit trouvé"),
        ApiResponse(responseCode = "404", description = "produit non trouvé")
    ])
    @Operation(summary = "Modifier un produit par son code")
    @PutMapping("/produit/{idProduit}")
    fun modifierProduit(@PathVariable idProduit: Int, @RequestBody produit: Produit): ResponseEntity<Void> {
        service.modifier(idProduit, produit)
        return ResponseEntity.ok().build()
    }
}
