package com.GaspillageZeroAPI.Controleurs

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
import java.net.URI

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

    @Operation(summary = "Permet d'ajouter une commande à la BD")
    @GetMapping("/utilisateur/{idUtilisateur}/commandes/")
    fun obtenirCommandesParUtilisateur(@PathVariable idUtilisateur: Int) = service.chercherCommandesParUtilisateur(idUtilisateur) ?: throw ExceptionRessourceIntrouvable("Les commandes de l'utilisateur avec le code $idUtilisateur sont introuvables")

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet d'obtenir d'obtenir la commande {idCommande} de l'utilisateur {idUtilisateur}")
    @GetMapping("/utilisateur/{idUtilisateur}/commande/{idCommande}")
    fun obtenirCommandeParUtilisateur(@PathVariable idUtilisateur: Int, @PathVariable idCommande: Int) = service.chercherCommandeParUtilisateur(idUtilisateur, idCommande)
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet d'obtenir une commande ayant le {idCommande} à l'épicerie ayant le {idÉpicerie}")
    @GetMapping("/épicerie/{idÉpicerie}/commande/{idCommande}")
    fun obtenirCommandeParÉpicerie(@PathVariable idÉpicerie: Int, @PathVariable idCommande: Int) = service.chercherCommandeParÉpicerie(idÉpicerie, idCommande)

    /**
    @GetMapping("/utilisateur/{idUtilisateur}/commande/{idCommande}/commandeDetail")
    fun obtenirCommandeDetailParUtilisateur(@PathVariable idUtilisateur: Int, @PathVariable idCommande: Int) = service.chercherCommandeDetailParUtilisateur(idUtilisateur, idCommande)

    @GetMapping("/utilisateur/{idUtilisateur}/commandes/historiqueCommande")
    fun obtenirHistoriqueCommandesDetailParUtilisateur(@PathVariable idUtilisateur: Int) = service.chercherHistoriqueCommandesDetailParUtilisateur(idUtilisateur)

    @GetMapping("/utilisateur/{idUtilisateur}/commandes/historiqueCommande/montant")
    fun obtenirArgentDépenséUtilisateur(@PathVariable idUtilisateur: Int) = service.obtenirArgentDépenséUtilisateur(idUtilisateur)
     **/
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Commande trouvée"),
        ApiResponse(responseCode = "404", description = "commande non trouvé")
    ])
    @Operation(summary = "Permet d'obtenir toutes les commande faites pour une épicerie ayant le {idÉpicerie}")
    @GetMapping("/épicerie/{idÉpicerie}/commandes")
    fun obtenirCommandesParÉpicerie(@PathVariable idÉpicerie: Int) = service.chercherCommandesParÉpicerie(idÉpicerie) ?: throw ExceptionRessourceIntrouvable("Les commandes de l'épicerie avec le id $idÉpicerie sont introuvables")

    /**
    @GetMapping("/épicerie/{idÉpicerie}/commande/{idCommande}/commandeDetail")
    fun obtenirCommandeDetailParÉpicerie(@PathVariable idÉpicerie: Int, @PathVariable idCommande: Int) = service.chercherCommandeDetailParÉpicerie(idÉpicerie, idCommande)

    @GetMapping("/épicerie/{idÉpicerie}/commandes/historiqueCommande")
    fun obtenirHistoriqueCommandesDetailParÉpicerie(@PathVariable idÉpicerie: Int) = service.chercherHistoriqueCommandesDetailParÉpicerie(idÉpicerie)

    @GetMapping("/épicerie/{idÉpicerie}/commandes/historiqueCommande/montant")
    fun obtenirArgentGagnéÉpicerie(@PathVariable idÉpicerie: Int) = service.obtenirArgentGagnéÉpicerie(idÉpicerie)
     **/
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
    fun suppimerCommande(@PathVariable idCommande: Int) {
        service.supprimer(idCommande)
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "La commande à été modifié avec succès!"),
        ApiResponse(responseCode = "404", description = "La commande est malheureusement introuvable.")
    ])

    @Operation(summary = "Permet de modifier les informations d'une commande")
    @PutMapping("/commande/{idCommande}")
    fun modifierCommande(@PathVariable idCommande: Int, @RequestBody commande: Commande){
        service.modifier(idCommande, commande)
    }


}