package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionAuthentification
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Services.CommandeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.lang.Exception
import java.net.URI
import java.security.Principal

@RestController
class CommandeControleur(val service: CommandeService) {

    @Operation(summary = "Obtenir la liste de toutes les commandes")
    @GetMapping("/commandes")
    fun obtenirCommandes() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Obtenir la commande par le ID de celui-ci")
    @GetMapping("/commande/{idCommande}")
    fun obtenirCommandeparCode(@PathVariable idCommande: Int) = service.chercherParCode(idCommande) ?: throw ExceptionRessourceIntrouvable("La commande avec le code $idCommande est introuvable")

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet d'obtenir toutes les commande faites pour l'utlisateur ayant le {idUtilisateur}")
    @GetMapping("/utilisateur/{idUtilisateur}/commandes")
    fun obtenirCommandesParUtilisateur(
            @PathVariable idUtilisateur: Int,
            principal: Principal?
    ): ResponseEntity<List<Commande>> {
        if(principal == null){
            throw ExceptionAuthentification("Veuillez vous authentifier")
        }
        val commandes = service.chercherCommandesParUtilisateur(idUtilisateur, principal.name)

        return if (commandes != null && commandes.isNotEmpty()) {
            ResponseEntity.ok(commandes)
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet d'obtenir toutes les commande faites pour une épicerie ayant le {idÉpicerie}")
    @GetMapping("/épicerie/{idÉpicerie}/commandes")
    fun obtenirCommandesParÉpicerie(@PathVariable idÉpicerie: Int, principal: Principal?): ResponseEntity<List<Commande>> {
        if(principal == null){
            throw ExceptionAuthentification("Vous devez vous authentifier")
        }

        val commandes = service.chercherCommandesParÉpicerie(idÉpicerie, principal.name)


        return if (commandes != null && commandes.isNotEmpty()) {
            ResponseEntity.ok(commandes)
        } else {
            ResponseEntity.notFound().build()
        }
    }



    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "La commande à été ajouter à la base de données"),
        ApiResponse(responseCode = "500", description = "Il y a eu un problème lors de l'ajout de la commande dans la base de données")
    ])
    @Operation(summary = "Permet d'ajouter une commande à la base de données")
    @PostMapping("/commande")
    fun ajouterCommande(@RequestBody commande: Commande): ResponseEntity<Commande> {
        val commande = service.ajouter(commande)

        if(commande != null) {
            val location: URI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{idCommande}")
                    .buildAndExpand(commande.idCommande)
                    .toUri()
            return ResponseEntity.created(location).body(commande)
        }

        return ResponseEntity.internalServerError().build()
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "la commande à été retiré avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est introuvable")
    ])
    @Operation(summary = "Permet de retirer une commande de la base de données")
    @DeleteMapping("/commande/{idCommande}")
    fun suppimerCommande(@PathVariable idCommande: Int, principal: Principal?) {
        if(principal == null){
            throw ExceptionAuthentification("Vous devez vous authentifier")
        }
        service.supprimer(idCommande, principal.name)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "La commande à été modifié avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est malheureusement introuvable.")
    ])

    @Operation(summary = "Permet de modifier les informations d'une commande")
    @PutMapping("/commande/{idCommande}")
    fun modifierCommande(@PathVariable idCommande: Int, principal: Principal?, @RequestBody commande: Commande){
        if(principal == null){
            throw ExceptionAuthentification("Vous devez vous authentifier")
        }
        service.modifier(idCommande, commande, principal.name)
    }


}