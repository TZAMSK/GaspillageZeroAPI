package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Services.CommandeService
import com.GaspillageZeroAPI.Services.LivraisonService
import com.GaspillageZeroAPI.Services.UtilisateurService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
class LivraisonControleur (val livraisonService: LivraisonService, val commandeService: CommandeService,
                           val utilisateurService: UtilisateurService) {

    //Pour accéder à la documentation OpenApi, visitez le lien suivant pour en savoir plus : http://localhost:8080/swagger-ui/index.html
    //@GetMapping("/listeLivraisons")
    //fun obtenirListeLivraisons() {

    //}


    //@GetMapping("/listeLivraisons/{code}")
    //fun obtenirGabaritProduitParCode(@PathVariable code: String)  {

    //}


    /*@PostMapping("/listeLivraisons")
    fun AjouterLivraison(@RequestBody livraison :Livraison) {

    }*/

    //@GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
    //fun obtenirCommandeÉpicParCode(@PathVariable idCommande : Int, @PathVariable idProduit: Int) {}

    @GetMapping("/evaluations")
    @Operation(summary = "Obtenir la liste des évaluations")
    @ApiResponse(responseCode = "200", description = "Liste des évaluations trouvées")
    @ApiResponse(responseCode = "404", description = "Liste des évaluations non-trouvées, veuillez réessayez...")
    fun obtenirEvaluations() {



    }

    @GetMapping("/evaluations/{code}")
    @Operation(summary = "Obtenir une évaluation en cherchant par code")
    @ApiResponse(responseCode = "200", description = "Évaluation trouvée")
    @ApiResponse(responseCode = "404", description = "Évaluation non-trouvée, veuillez réessayez...")
    fun obtenirEvaluationParCode(@PathVariable code: Int) {
        TODO("Méthode non-implémentée")
    }

    @GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons")
    @Operation(summary = "Obtenir la liste des livraisons")
    @ApiResponse(responseCode = "200", description = "Liste des livraisons trouvées")
    @ApiResponse(responseCode = "404", description = "Liste des livraisons non-trouvées, veuillez réessayez...")
    fun obtenirLivraisons(@PathVariable codeUtilisateur: Int, @PathVariable idÉpicerie: Int,
                          @PathVariable idCommande: Int): ResponseEntity<List<Livraison>> {
        val utilisateur = utilisateurService.chercherParCode(codeUtilisateur)
        val commande = commandeService.chercherParCode(idCommande)

        return if (utilisateur != null && commande != null) {
            val livraisons = livraisonService.obtenirLivraisons()
            ResponseEntity.status(HttpStatus.OK).body(livraisons)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeCommande}")
    //@Operation(summary = "Obtenir une livraison en cherchant par code")
    //@ApiResponse(responseCode = "200", description = "Livraison trouvée")
    //@ApiResponse(responseCode = "404", description = "Livraison non-trouvée, veuillez réessayez...")
    fun obtenirLivraisonParCode(@PathVariable code_utilisateur: Int,
                                @PathVariable idCommande: Int, @PathVariable codeCommande: Int): ResponseEntity<Livraison> {
        val utilisateur = utilisateurService.chercherParCode(code_utilisateur)
        val commande = commandeService.chercherParCode(idCommande)
        val livraison_existante = livraisonService.obtenirLivraisonParCode(codeCommande)

        if (utilisateur != null && commande != null && livraison_existante != null) {
            return ResponseEntity.status(HttpStatus.OK).body(livraison_existante)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    @Operation(summary = "Ajouté une livraison")
    @ApiResponse(responseCode = "201", description = "La livraison a été ajouté avec succès!")
    fun inscrireLivraison(@PathVariable code_utilisateur: Int,
        @PathVariable idCommande: Int, @RequestBody livraison: Livraison): ResponseEntity<String> {
        try {
            val utilisateur = utilisateurService.chercherParCodeBD(code_utilisateur)
            val commande = commandeService.chercherParCode(idCommande)

            if (utilisateur != null && commande != null) {
                livraisonService.ajouterLivraison(livraison)

                return ResponseEntity.status(HttpStatus.CREATED).body("La livraison a été ajouté avec succès!")
            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'id de l'utilisateur ou de la commande est invalide.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout d'une livraison.")
        }
    }

    @PutMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    @Operation(summary = "Modifier une livraison")
    @ApiResponse(responseCode = "204", description = "La livraison a été modifiée avec succès!")
    fun majLivraison(@PathVariable code_utilisateur: Int, @PathVariable idCommande: Int,
                     @PathVariable code: Int, @RequestBody livraison: Livraison): ResponseEntity<Int> {
        val utilisateur = utilisateurService.chercherParCodeBD(code_utilisateur)
        val commande = commandeService.chercherParCode(idCommande)
        val livraison_existante = livraisonService.obtenirLivraisonParCode(code)

        return if (utilisateur != null && commande != null && livraison_existante != null) {
            livraisonService.modifierLivraison(code, livraison)
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    @Operation(summary = "Supprimer une livraison")
    @ApiResponse(responseCode = "204", description = "La livraison a été supprimée avec succès!")
    fun supprimerLivraison(@PathVariable code_utilisateur: Int, @PathVariable idCommande: Int,
        @PathVariable code: Int): ResponseEntity<Int> {
        val utilisateur = utilisateurService.chercherParCodeBD(code_utilisateur)
        val commande = commandeService.chercherParCode(idCommande)
        val livraison_existante = livraisonService.obtenirLivraisonParCode(code)

        return if (utilisateur != null && commande != null && livraison_existante != null) {
            livraisonService.supprimerLivraison(code)
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}