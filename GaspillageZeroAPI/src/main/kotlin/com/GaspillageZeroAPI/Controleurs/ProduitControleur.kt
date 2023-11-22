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
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "Commande non trouvé")
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
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "Commande non trouvé")
    ])

    @Operation(summary = "Obtenir la liste de toutes les produits par le ID de l'épicerie")
    @GetMapping("/épicerie/{idÉpicerie}/produits")
    fun obtenirProduitÉpicerie(@PathVariable idÉpicerie: Int): List<Produit> {
        val épicerie = service.chercherParÉpicerie(idÉpicerie)
        if(épicerie == null){
            throw ÉpicerieIntrouvableException("L'épicerie de code $idÉpicerie est introuvable")
        }
        return épicerie
    }
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "Commande non trouvé")
    ])

    @Operation(summary = "Obtenir le produit par son ID et l'id de l'épicerie")
    @GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
    fun obtenirProduitÉpicerieParCode(@PathVariable idÉpicerie: Int, @PathVariable idProduit: Int): Produit? {
        val produit = service.chercherParÉpicerieParCode(idÉpicerie, idProduit)
        if(produit == null){
            throw ProduitIntrouvableException("Le produit de code $idProduit est introuvable")
        }
        return produit
    }


    @Operation(summary = "Permet d'ajouter une produit à la base de données")
    @PostMapping("/produit")
    fun ajouterProduit(@RequestBody produit: Produit) {
        service.ajouter(produit)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "la commande à été retiré avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est introuvable")
    ])

    @Operation(summary = "Permet de retirer une produit de la base de données")
    @DeleteMapping("/produit/delete/{idProduit}")
    fun supprimerProduit(@PathVariable idProduit: Int) {
        val produit = service.supprimer(idProduit)
        if(produit == null){
            throw ProduitIntrouvableException("Le produit de code $idProduit est introuvable")
        }
        service.supprimer(idProduit)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "Commande non trouvé")
    ])

    @Operation(summary = "Permet de modifier les informations d'une produit")
    @PutMapping("/produit/save/{idProduit}")
    fun modifierProduit(@PathVariable idProduit: Int, @RequestBody produit: Produit) {
        val modproduit = service.modifier(idProduit,produit)
        if(modproduit == null){
            throw ProduitIntrouvableException("Le produit de code $idProduit est introuvable")
        }
        service.modifier(idProduit, produit)
    }


}