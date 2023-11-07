package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Panier
import com.GaspillageZeroAPI.Services.PanierService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
class PanierControleur(val service: PanierService) {

    @Operation(summary = "Permet d'obtenir la liste de tout les paniers")
    @GetMapping("/paniers")
    fun obtenirPaniers() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet d'obtenir le panier avec le {idPanier}")
    @GetMapping("/panier/{idPanier}")
    fun obtenirPanierparCode(@PathVariable idPanier: Int) = service.chercherParCode(idPanier)

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet d'obtenir le panier étant attribué à la commande ayant le {idCommande}")
    @GetMapping("/commande/{idCommande}/panier")
    fun obtenirContenuParCommande(@PathVariable idCommande: Int) = service.chercherContenuParCommande(idCommande)

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "La commande à été ajouter à la base de données"),
        ApiResponse(responseCode = "409", description = "Le produit existe déjà dans la base de données")
    ])
    @Operation(summary = "Permet d'ajouter un panier à la base de données")
    @PostMapping("/panier")
    fun ajouterPanier(@RequestBody panier: Panier) {
        service.ajouter(panier)
    }

    @DeleteMapping("/panier/delete/{idPanier}")
    fun suppimerPanier(@PathVariable idPanier: Int) {
        service.supprimer(idPanier)
    }

    @PutMapping("/panier/save")
    fun modifierPanier(@PathVariable idPanier: Int, @RequestBody panier: Panier){
        service.modifier(idPanier, panier)
    }
}