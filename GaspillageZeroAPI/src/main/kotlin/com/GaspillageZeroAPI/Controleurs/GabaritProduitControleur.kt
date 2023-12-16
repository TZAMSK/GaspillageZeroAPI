package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRequeteInvalide
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Modèle.GabaritProduitDto
import com.GaspillageZeroAPI.Services.GabaritProduitService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.ByteArrayOutputStream
import java.util.*
import javax.sql.rowset.serial.SerialBlob

@RestController
class GabaritProduitController(val service: GabaritProduitService) {

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduits trouvés"),
        ApiResponse(responseCode = "404", description = "Gabaritproduits non trouvés"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Obtenir la liste des gabaritproduits")
    @GetMapping("/gabaritproduits")
    fun obtenirGabaritProduits(): ResponseEntity<List<GabaritProduitDto>> {
        try {
            val gabarits = service.chercherTous()
            val gabaritsDto = gabarits.map { gabarit ->
                convertirEnDto(gabarit)
            }

            if (gabaritsDto.isNotEmpty()) {
                return ResponseEntity.ok(gabaritsDto)
            } else {
                throw ExceptionRessourceIntrouvable("Gabaritproduits non trouvés")
            }
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur", e)
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduit trouvé"),
        ApiResponse(responseCode = "404", description = "Gabaritproduit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées")
    ])
    @Operation(summary = "Obtenir un gabaritproduit par son ID")
    @GetMapping("/gabaritproduit/{idGabaritProduit}")
    fun obtenirGabaritProduitParCode(@PathVariable idGabaritProduit: Int): ResponseEntity<GabaritProduitDto> {
        return try {
            val gabarit = service.chercherParCode(idGabaritProduit)
            gabarit?.let {
                ResponseEntity.ok(convertirEnDto(it))
            } ?: throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Gabaritproduit créé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées"),
        ApiResponse(responseCode = "409", description = "Conflit: Violation de contrainte"),
        ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    ])
    @Operation(summary = "Ajouter un gabaritproduit")
    @PostMapping("/gabaritproduit")
    fun ajouterGabarit(@RequestBody gabaritProduitDto: GabaritProduitDto): ResponseEntity<Void> {
        return try {
            val gabaritProduit = convertirEnEntité(gabaritProduitDto)
            service.ajouter(gabaritProduit)
            ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (e: DataIntegrityViolationException) {
            throw ExceptionConflitRessourceExistante("Conflit: Violation de contrainte")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide ou données mal formées")
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur interne du serveur lors de l'ajout du gabaritproduit")
        }
    }

    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduit supprimé"),
        ApiResponse(responseCode = "404", description = "Gabaritproduit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide")
    ])
    @Operation(summary = "Supprimer un gabaritproduit par son ID")
    @DeleteMapping("/gabaritproduit/{idGabaritProduit}")
    fun supprimerGabarit(@PathVariable idGabaritProduit: Int): ResponseEntity<Void> {
        return try {
            if (service.supprimer(idGabaritProduit)) {
                ResponseEntity.ok().build()
            } else {
                throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
            }
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
        }
    }


    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Gabaritproduit modifié"),
        ApiResponse(responseCode = "404", description = "Gabaritproduit non trouvé"),
        ApiResponse(responseCode = "400", description = "Requête invalide ou données mal formées"),
        ApiResponse(responseCode = "409", description = "Conflit: Violation de contrainte")
    ])
    @Operation(summary = "Modifier un gabaritproduit par son ID")
    @PutMapping("/gabaritproduit/{idGabaritProduit}")
    fun modifierGabarit(@PathVariable idGabaritProduit: Int, @RequestBody gabaritProduitDto: GabaritProduitDto): ResponseEntity<Void> {
        return try {
            val gabaritProduit = convertirEnEntité(gabaritProduitDto)
            if (service.modifier(idGabaritProduit, gabaritProduit)) {
                ResponseEntity.ok().build()
            } else {
                throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
            }
        } catch (e: DataIntegrityViolationException) {
            throw ExceptionConflitRessourceExistante("Conflit: Violation de contrainte pour l'ID $idGabaritProduit")
        } catch (e: IllegalArgumentException) {
            throw ExceptionRequeteInvalide("Requête invalide ou données mal formées : ${e.message}")
        } catch (e: Exception) {
            throw ExceptionRessourceIntrouvable("Gabaritproduit avec l'ID $idGabaritProduit non trouvé")
        }
    }

    // Méthodes de Conversions Blob/Base64
    fun convertirEnDto(gabaritProduit: GabaritProduit): GabaritProduitDto {
        val imageBase64 = gabaritProduit.image?.let { blob ->
            // Conversion de Blob en Base64
            ByteArrayOutputStream().use { outputStream ->
                blob.binaryStream.use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
                Base64.getEncoder().encodeToString(outputStream.toByteArray())
            }
        }
        return GabaritProduitDto(
            idGabaritProduit = gabaritProduit.idGabaritProduit,
            nom = gabaritProduit.nom,
            description = gabaritProduit.description,
            image = imageBase64,
            categorie = gabaritProduit.categorie,
            épicerie = gabaritProduit.épicerie
        )
    }

    fun convertirEnEntité(gabaritProduitDto: GabaritProduitDto): GabaritProduit {
        val imageBlob = gabaritProduitDto.image?.let { base64 ->
            // Conversion de Base64 en Blob
            SerialBlob(Base64.getDecoder().decode(base64))
        }
        return GabaritProduit(
            idGabaritProduit = gabaritProduitDto.idGabaritProduit,
            nom = gabaritProduitDto.nom,
            description = gabaritProduitDto.description,
            image = imageBlob,
            categorie = gabaritProduitDto.categorie,
            épicerie = gabaritProduitDto.épicerie
        )
    }


}
