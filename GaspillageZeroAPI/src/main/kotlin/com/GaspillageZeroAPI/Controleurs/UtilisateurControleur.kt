package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Services.UtilisateurService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
class UtilisateurControleur(val service: UtilisateurService) {

    @Operation(summary = "Permet d'obtenir la liste de tout les utilisateur")
    @GetMapping("/utilisateurs")
    fun obtenirUtilisateurs() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "permet d'obtenir l'utilisateur ayant le ID {idUtilisateur}")
    @GetMapping("/utilisateur/{idUtilisateur}")
    fun obtenirUtilisateurparCode(@PathVariable idUtilisateur: Int) = service.chercherParCode(idUtilisateur) ?: throw ExceptionRessourceIntrouvable("L'utilisateur avec le id $idUtilisateur est introuvable")

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "La commande à été ajouter à la base de données"),
        ApiResponse(responseCode = "409", description = "Le produit existe déjà dans la base de données")
    ])
    @Operation(summary = "Permet d'ajouter un utilisateur à la base de données")
    @PostMapping("/utilisateur")
    fun ajouterUtilisateur(@RequestBody utilisateur: Utilisateur): ResponseEntity<Utilisateur> {
        val utilisateur = service.ajouter(utilisateur)

        if(utilisateur != null) {
            val location: URI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{idUtilisateur}")
                    .buildAndExpand(utilisateur.idUtilisateur)
                    .toUri()
            return ResponseEntity.created(location).body(utilisateur)
        }

        return ResponseEntity.internalServerError().build()
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet de supprimer un utilisateur de la base de données")
    @DeleteMapping("/utilisateur/delete/{idUtilisateur}")
    fun suppimerUtilisateur(@PathVariable idUtilisateur: Int) {
        service.supprimer(idUtilisateur)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet de modifier les informations d'un utilisateur")
    @PutMapping("/utilisateur/modifier/{idUtilisateur}")
    fun modifierUtilisateur(@PathVariable idUtilisateur: Int, @RequestBody utilisateur: Utilisateur){
        service.modifier(idUtilisateur, utilisateur)
    }
}