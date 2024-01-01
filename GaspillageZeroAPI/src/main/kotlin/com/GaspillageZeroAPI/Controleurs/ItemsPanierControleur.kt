package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionAuthentification
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.ItemsPanier
import com.GaspillageZeroAPI.Services.ItemsPanierService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("\${api.base-path:}")
@Tag(
    name = "ItemsPanier",
    description = "Points d'accès aux ressources liées aux items paniers du service."
)
class ItemsPanierControleur(val service: ItemsPanierService) {

    @Operation(
        summary = "Obtenir la liste de toutes les items paniers",
        description = "Retourne la liste de tous les items paniers au service.",
        operationId = "obtenirItemsPanier",
        responses = [
            ApiResponse(responseCode = "200", description = "Une liste de items panier a été retournée."),
        ]
    )
    @GetMapping(
        value = ["/itemsPanier"],
        produces = ["application/json"])
    fun obtenirCommandes() = service.chercherTous()

    @Operation(
        summary = "Obtenir une item panier par son code",
        description = "Retourne une item panier grâce à son ID.",
        operationId = "obtenirItemsPanier",
        responses = [
            ApiResponse(responseCode = "200", description = "Une liste de items panier a été retournée."),
            ApiResponse(responseCode = "404", description = "Le menu recherché n'existe pas dans le service.")
        ]
    )
    @GetMapping(
        value = ["/itemsPanier/{idItemsPanier}"],
        produces = ["application/json"])
    fun obtenirItemsPanierparCode(@PathVariable idItemsPanier: Int) = service.chercherParCode(idItemsPanier) ?: throw ExceptionRessourceIntrouvable("L'item de panier avec le code $idItemsPanier est introuvable")

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "L'item de panier à été retiré avec succès!"),
        ApiResponse(responseCode = "404", description = "L'item de panier est introuvable"),
        ApiResponse(responseCode = "401", description = "L'utilisateur voulant effectuer l'opération n'est pas correctement authentifié."),
        ApiResponse(responseCode = "403", description = "L'utilisateur voulant effectuer l'opération n'a pas les droits nécessaires."),
    ])
    @Operation(summary = "Permet de retirer une commande de la base de données")
    @DeleteMapping("/itemsPanier/{idItemsPanier}")
    fun suppimerItemsPanier(@PathVariable idItemsPanier: Int, principal: Principal?): ResponseEntity<ItemsPanier> {
        if(principal == null){
            throw ExceptionAuthentification("Vous devez vous authentifier")
        }
        service.supprimer(idItemsPanier, principal.name)
        return ResponseEntity.noContent().build()
    }
}