package com.example.gaspillage_alimentaire_api.Controleurs

import org.springframework.web.bind.annotation.*
import com.example.gaspillage_alimentaire_api.Modèle.Livraison
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse

@RestController
@RequestMapping("\${api.base-path:}")
class LivraisonControleur {

    //Pour accéder à la documentation OpenApi, visitez le lien suivant pour en savoir plus : http://localhost:8080/swagger-ui/index.html

    @GetMapping("/evaluations")
    @Operation(summary = "Obtenir la liste des évaluations")
    @ApiResponse(responseCode = "200", description = "Liste des évaluations trouvées")
    @ApiResponse(responseCode = "404", description = "Liste des évaluations non-trouvées, veuillez réessayez...")
    fun obtenirEvaluations() {
        TODO("Méthode non-implémentée")
    }

    @GetMapping("/evaluations/{code}")
    @Operation(summary = "Obtenir une évaluation en cherchant par code")
    @ApiResponse(responseCode = "200", description = "Évaluation trouvée")
    @ApiResponse(responseCode = "404", description = "Évaluation non-trouvée, veuillez réessayez...")
    fun obtenirEvaluationParCode(@PathVariable code: String) {
        TODO("Méthode non-implémentée")
    }

    @GetMapping("/livraisons")
    @Operation(summary = "Obtenir la liste des livraisons")
    @ApiResponse(responseCode = "200", description = "Liste des livraisons trouvées")
    @ApiResponse(responseCode = "404", description = "Liste des livraisons non-trouvées, veuillez réessayez...")
    fun obtenirLivraisons() {
        TODO("Méthode non-implémentée")
    }

    @GetMapping("/livraisons/{code}")
    @Operation(summary = "Obtenir une livraison en cherchant par code")
    @ApiResponse(responseCode = "200", description = "Livraison trouvée")
    @ApiResponse(responseCode = "404", description = "Livraison non-trouvée, veuillez réessayez...")
    fun obtenirLivraisonParCode(@PathVariable code: String) {
        TODO("Méthode non-implémentée")
    }

    @PostMapping("/livraisons")
    @Operation(summary = "Ajouté une livraison")
    @ApiResponse(responseCode = "201", description = "La livraison a été ajouté avec succès!")
    fun inscrireLivraison() {
        TODO("Méthode non-implémentée")
    }

    @PutMapping("/livraisons/{code}")
    @Operation(summary = "Modifier une livraison")
    @ApiResponse(responseCode = "200", description = "La livraison a été modifiée avec succès!")
    fun majLivraison(@PathVariable code: String) {
        TODO("Méthode non-implémentée")
    }

    @DeleteMapping("/livraisons/{code}")
    @Operation(summary = "Supprimer une livraison")
    @ApiResponse(responseCode = "204", description = "La livraison a été supprimée avec succès!")
    fun supprimerLivraison(@PathVariable code: String){
        TODO("Méthode non-implémentée")
    }
}