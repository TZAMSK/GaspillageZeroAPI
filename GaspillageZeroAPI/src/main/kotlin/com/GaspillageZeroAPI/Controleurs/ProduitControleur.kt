package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ProduitIntrouvableException
import com.GaspillageZeroAPI.Exceptions.ÉpicerieIntrouvableException
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ProduitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*


@RestController
class ProduitControleur(val service: ProduitService) {

    @Operation(summary = "Obtenir la liste de toutes les produits")
    @GetMapping("/produits")
    fun obtenirProduits() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produits trouvés"),
        ApiResponse(responseCode = "404", description = "Produits non trouvés")
    ])

    @Operation(summary = "Obtenir le produit par le ID de celui-ci")
    @GetMapping("/produit/{idProduit}")
    fun obtenirProduitParCode(@PathVariable idProduit: Int): Produit?{
        val produit = service.chercherParCode(idProduit)
        if(produit == null){
            throw ProduitIntrouvableException("Le produit de code $idProduit est introuvable")
        }
        return produit
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produit trouvé"),
        ApiResponse(responseCode = "404", description = "Produit non trouvé")
    ])

    @Operation(summary = "Obtenir la liste de toutes les produits par le ID de l'épicerie")
    @GetMapping("/épicerie/{idÉpicerie}/produits")
    fun obtenirProduitÉpicerie(@PathVariable idÉpicerie: Int): List<Produit> {
        val produits = service.chercherParÉpicerie(idÉpicerie)
        if(produits == null){
            throw ÉpicerieIntrouvableException("Les produits l'épicerie de code $idÉpicerie sont introuvables")
        }
        return produits
    }
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produits trouvés"),
        ApiResponse(responseCode = "404", description = "Produits non trouvés")
    ])
    @Operation(summary = "Obtenir le produit par son ID et l'id de l'épicerie")
    @GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
    fun obtenirProduitÉpicerieParCode(@PathVariable idÉpicerie: Int, @PathVariable idProduit: Int): Produit? {
        val produit = service.chercherParÉpicerieParCode(idÉpicerie, idProduit)
        if(produit == null){
            throw ProduitIntrouvableException("Le produit de code $idProduit avec épicerie code $idÉpicerie est introuvable")
        }
        return produit
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Produits trouvés"),
        ApiResponse(responseCode = "404", description = "Produits non trouvés")
    ])
    @Operation(summary = "Permet d'ajouter une produit à la base de données")
    @PostMapping("/produit")
    fun ajouterProduit(@RequestBody produit: Produit) {
        service.ajouter(produit)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "le Produit à été retiré avec succès!"),
        ApiResponse(responseCode = "404", description = "Le Produit est introuvable")
    ])

    @Operation(summary = "Permet de retirer un produit de la base de données")
    @DeleteMapping("/produit/{idProduit}")
    fun supprimerProduit(@PathVariable idProduit: Int) {
        service.supprimer(idProduit)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produit trouvé"),
        ApiResponse(responseCode = "404", description = "Produit non trouvé")
    ])

    @Operation(summary = "Permet de modifier les informations d'un produit")
    @PutMapping("/produit/{idProduit}")
    fun modifierProduit(@PathVariable idProduit: Int, @RequestBody produit: Produit) {
        service.modifier(idProduit, produit)
    }


}