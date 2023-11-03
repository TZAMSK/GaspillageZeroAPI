package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Services.CommandeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController
class CommandeControleur(val service: CommandeService) {

    @Operation(summary = "Obtenir la liste de toutes les commandes")
    @GetMapping("/commandes")
    fun obtenirCommandes() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande nonn trouvé")
    ])

    @Operation(summary = "Obtenir la commande par le ID de celui-ci")
    @GetMapping("/commande/{idCommande}")
    fun obtenirCommandeparCode(@PathVariable idCommande: Int) = service.chercherParCode(idCommande)

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "La commande à été ajouter à la base de données"),
        ApiResponse(responseCode = "409", description = "Le produit existe déjà dans la base de données")
    ])

    @Operation(summary = "Permet d'ajouter une commande à la BD")
    @PostMapping("/commande")
    fun ajouterCommande(@RequestBody commande: Commande) {
        service.ajouter(commande)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "la commande à été retiré avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est introuvable")
    ])

    @Operation(summary = "Permet de retirer une commande de la base de données")
    @DeleteMapping("/commande/delete/{idCommande}")
    fun suppimerCommande(@PathVariable idCommande: Int) {
        service.supprimer(idCommande)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "La commande à été modifié avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est malheureusement introuvable.")
    ])

    @Operation(summary = "Permet de modifier les informations d'une commande")
    @PutMapping("/commande/save")
    fun modifierCommande(@PathVariable idCommande: Int, @RequestBody commande: Commande){
        service.modifier(idCommande, commande)
    }


}