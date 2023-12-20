package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionAuthentification
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Services.UtilisateurService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.security.Principal

@RestController
@RequestMapping("\${api.base-path:}")
@Tag(
        name = "Utilisateur",
        description = "Points d'accès aux ressources liées aux utilisateur du service."
)
class UtilisateurControleur(val service: UtilisateurService) {

    @Operation(summary = "Permet d'obtenir la liste de tout les utilisateur")
    @GetMapping("/utilisateurs")
    fun obtenirUtilisateurs() = service.chercherTous()

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Utilisateur trouvée"),
        ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    ])
    @Operation(summary = "permet d'obtenir l'utilisateur ayant le ID {idUtilisateur}")
    @GetMapping("/utilisateur/{idUtilisateur}")
    fun obtenirUtilisateurparCode(@PathVariable idUtilisateur: Int) = service.chercherParCode(idUtilisateur) ?: throw ExceptionRessourceIntrouvable("L'utilisateur avec le id $idUtilisateur est introuvable")

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "L'utilisateur à été ajouter à la base de données"),
        ApiResponse(responseCode = "409", description = "L'utilisateur existe déjà dans la base de données")
    ])
    @Operation(summary = "Permet d'ajouter un utilisateur à la base de données")
    @PostMapping("/utilisateur")
    fun ajouterUtilisateur(@RequestBody utilisateur: Utilisateur): ResponseEntity<Utilisateur> {
        try {
            val utilisateurAjoute = service.ajouter(utilisateur)

            if (utilisateurAjoute != null) {
                val location: URI = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{idUtilisateur}")
                        .buildAndExpand(utilisateurAjoute.code)
                        .toUri()

                return ResponseEntity.created(location).body(utilisateurAjoute)
            }
            return ResponseEntity.internalServerError().build()
        } catch (e: ExceptionConflitRessourceExistante) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        } catch (e: ExceptionErreurServeur) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Utilisateur trouvée"),
        ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    ])
    @Operation(summary = "Permet de supprimer un utilisateur de la base de données")
    @DeleteMapping("/utilisateur/{idUtilisateur}")
    fun suppimerUtilisateur(@PathVariable idUtilisateur: Int): ResponseEntity<Utilisateur> {
        if (service.chercherParCode(idUtilisateur) == null) {
            return ResponseEntity.notFound().build()
        }
        service.supprimer(idUtilisateur);
        return ResponseEntity.noContent().build()
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Utilisateur trouvée"),
        ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    ])
    @Operation(summary = "Permet de modifier les informations d'un utilisateur")
    @PutMapping("/utilisateur/{idUtilisateur}")
    fun modifierUtilisateur(
            @PathVariable idUtilisateur: Int,
            code_util: Principal?,
            @RequestBody utilisateur: Utilisateur
    ): ResponseEntity<Utilisateur> {
        if(code_util == null){
            throw ExceptionAuthentification("L'utilisateur doit être authentifié pour pouvoir modifier")
        }

        val updatedUtilisateur = service.modifier(idUtilisateur, code_util.name, utilisateur)

        return if (updatedUtilisateur != null) {
            ResponseEntity.ok(updatedUtilisateur)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}