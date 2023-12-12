package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Exceptions.LivraisonIntrouvableException
import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Évaluation
import com.GaspillageZeroAPI.Services.LivraisonService
import com.GaspillageZeroAPI.Services.ÉvaluationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.apache.coyote.Response
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder
import java.net.PasswordAuthentication
import java.net.URI


@RestController
class LivraisonControleur (val livraisonService: LivraisonService, val évaluationService : ÉvaluationService) {
    //Pour accéder à la documentation OpenApi, visitez le lien suivant pour en savoir plus : http://localhost:8080/swagger-ui/index.html

    @GetMapping("/evaluations")
    @Operation(summary = "Obtenir la liste des évaluations")
    @ApiResponse(responseCode = "200", description = "Liste des évaluations trouvées")
    @ApiResponse(responseCode = "404", description = "Liste des évaluations non-trouvées, veuillez réessayez...")
    fun obtenirTousÉvalutions() = évaluationService.obtenirÉvaluations()

    @GetMapping("/evaluations/{code}")
    @Operation(summary = "Obtenir une évaluation en cherchant par code")
    @ApiResponse(responseCode = "200", description = "Évaluation trouvée")
    @ApiResponse(responseCode = "404", description = "Évaluation non-trouvée, veuillez réessayez...")
    fun obtenirEvaluationParCode(@PathVariable code: Int) : Évaluation? {
        val avis = évaluationService.chercherParCodeÉvaluation(code)
        if(avis == null){
            throw LivraisonIntrouvableException("L'avis avec le code $code est introuvable")
        }
        return avis
    }

    @GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons")
    @Operation(summary = "Obtenir la liste des livraisons")
    @ApiResponse(responseCode = "200", description = "Liste des livraisons trouvées")
    @ApiResponse(responseCode = "404", description = "Liste des livraisons non-trouvées, veuillez réessayez...")
    fun obtenirLivraisons(@PathVariable code_utilisateur: Int,
                          @PathVariable idCommande: Int) = livraisonService.obtenirLivraisons()

    @GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeLivraison}")
    @Operation(summary = "Obtenir une livraison en cherchant par code")
    @ApiResponse(responseCode = "200", description = "Livraison trouvée")
    @ApiResponse(responseCode = "404", description = "Livraison non-trouvée, veuillez réessayez...")
    fun obtenirLivraisonParCode(@PathVariable code_utilisateur: Int,
                                @PathVariable idCommande: Int, @PathVariable codeLivraison: Int) =

        livraisonService.obtenirLivraisonParCode(codeLivraison) ?: throw ExceptionRessourceIntrouvable("La livraison avec le code $codeLivraison est invalide.")

    @PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    @Operation(summary = "Ajouté une livraison")
    @ApiResponse(responseCode = "201", description = "Ce code signifie que la livraison a bien été ajouté dans la base de données.")
    @ApiResponse(responseCode = "409", description = "Ce code est retourné lorsqu'on essaye d'ajouter une livraison qui existe déjà.")
    @ApiResponse(responseCode = "500", description = "Ce code signifie qu'il y a un problème interne dans le serveur.")
    fun inscrireLivraison(@PathVariable code_utilisateur: Int,
        @PathVariable idCommande: Int, @RequestBody livraison: Livraison,
                          uriComponentsBuilder: UriComponentsBuilder) : ResponseEntity<Livraison> {
        val livraisonExistante = livraison.code?.let { livraisonService.obtenirLivraisonExistanteParCode(it) }

        return if (livraisonExistante == 0) {
            val nouvelleLivraison = livraisonService.ajouterLivraison(livraison)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeLivraison}")
                .buildAndExpand(code_utilisateur, idCommande, livraison.code)
                .toUri()

            ResponseEntity.created(location).body(nouvelleLivraison)
        } else if (livraisonExistante == 1){
            throw ExceptionConflitRessourceExistante("La livraison avec le numéro de code ${livraison.code} est déjà inscrit au service.")
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PutMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    @Operation(summary = "Modifier une livraison")
    @ApiResponse(responseCode = "204", description = "La livraison a été modifiée avec succès!")
    fun majLivraison(@PathVariable code_utilisateur: Int, @PathVariable idCommande: Int,
                     @PathVariable code: Int, @RequestBody livraison: Livraison) {
        livraisonService.modifierLivraison(code, livraison)
    }

    @DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    @Operation(summary = "Supprimer une livraison")
    @ApiResponse(responseCode = "204", description = "La livraison a été supprimée avec succès!")
    fun supprimerLivraison(@PathVariable code: Int, authentication: Neo4jProperties.Authentication) : ResponseEntity<String> {
        val username = authentication.username

      return if (username == "Gaston") {
            livraisonService.supprimerLivraison(code)
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
    }
}