package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.GabaritProduitIntrouvableException
import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Services.AdresseService
import com.GaspillageZeroAPI.Services.GabaritProduitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
class GabaritProduitController(val service: GabaritProduitService) {

    // MÉTHODES

    // MÉTHODE AFFICHAGES - LISTE GABARITS PRODUITS
    @Operation(summary = "Obtenir la liste de toutes les gabarits des produits")
    @GetMapping("/gabaritproduits")
    fun obtenirGabaritProduits() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "Commande non trouvé")
    ])

    // MÉTHODE AFFICHAGES 2 - GABARIT PRODUIT PAR CODE
    @Operation(summary = "Obtenir le gabarit par le ID de celui-ci")
    @GetMapping("/gabaritproduit/{idGabaritProduit}")
    fun obtenirGabaritProduitParCode(@PathVariable idGabaritProduit: Int): GabaritProduit?{
        val gabarit = service.chercherParCode(idGabaritProduit)
        if(gabarit == null){
            throw GabaritProduitIntrouvableException("Le gabarit de code $idGabaritProduit est introuvable")
        }
        return gabarit
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "Commande non trouvé")
    ])

    // MÉTHODE AJOUTER - AJOUTER GABARIT PRODUIT
    @Operation(summary = "Permet d'ajouter une gabarit à la base de données")
    @PostMapping("/gabaritproduits")
    fun ajouterGabarit(@RequestBody gabaritProduit: GabaritProduit){
        service.ajouter(gabaritProduit)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "la commande à été retiré avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est introuvable")
    ])

    //MÉTHODE SUPPRIMER - SUPPRIMER GABARIT PRODUIT PAR CODE
    @Operation(summary = "Permet de retirer une gabarit de la base de données")
    @DeleteMapping("/gabaritproduit/{idGabaritProduit}")
    fun supprimerGabarit(@PathVariable idGabaritProduit: Int){
        val gabarit = service.supprimer(idGabaritProduit)
        if(gabarit == null){
            throw GabaritProduitIntrouvableException("Le gabarit de code $idGabaritProduit est introuvable")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "la commande à été retiré avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est introuvable")
    ])

    // MÉTHODE MODIFICATION - MODIFIER GABARIT PRODUIT PAR CODE
    @Operation(summary = "Permet de modifier les informations d'une gabarit")
    @PutMapping("/gabaritproduit/{idGabaritProduit}")
    fun modifierGabarit(@PathVariable idGabaritProduit: Int, @RequestBody gabaritProduit: GabaritProduit){
        val gabarit = service.modifier(idGabaritProduit,gabaritProduit)
        if(gabarit == null){
            throw GabaritProduitIntrouvableException("Le gabarit de code $idGabaritProduit est introuvable")
        }
        service.modifier(idGabaritProduit,gabaritProduit)
    }



}