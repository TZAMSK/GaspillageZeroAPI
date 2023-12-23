package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Services.GabaritProduitService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.Date

@SpringBootTest
@AutoConfigureMockMvc
class GabaritProduitControleurTest {
/*
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: GabaritProduitService

    @Autowired
    private lateinit var mapper: ObjectMapper

    private fun créationÉchantillonGabaritProduit(id: Int, nom: String, description: String, categorie: String): GabaritProduit {
        val produits = listOf(
            SourceDonnées.produits[0],
                SourceDonnées.produits[1]
        )
        return GabaritProduit(id, nom, description, null, categorie, SourceDonnées.épiceries[0])
    }

    @Test
    fun `Étant donnée le GabaritProduit avec le code 3, lorsqu'on éffectue une requète GET alors on obtient un GabaritProduit dans un format JSON avec le id 3 et un code 200 `() {
        val gabaritProduit = créationÉchantillonGabaritProduit(3, "NomGabarit", "Description", "Catégorie")
        Mockito.`when`(service.chercherParCode(3)).thenReturn(gabaritProduit)

        mockMvc.perform(get("/gabaritproduit/3"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.idGabaritProduit").value(3))
            .andExpect(jsonPath("$.nom").value("NomGabarit"))
    }

    @Test
    fun `Étant donnée le GabaritProduit avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`() {
        Mockito.`when`(service.chercherParCode(4)).thenThrow(ExceptionRessourceIntrouvable("Le gabarit de code 4 est introuvable"))

        mockMvc.perform(get("/gabaritproduit/4"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Étant donnée un GabaritProduit avec le code 4, lorsqu'on ajoute un GabaritProduit à l'épicerie avec le code 1 à l'aide d'une requête POST, on obtient le code 201`() {
        val gabaritProduit = créationÉchantillonGabaritProduit(1, "NouveauGabarit", "DescriptionNouvelle", "CatégorieNouvelle")
        val codeUtilisateur = "testUser"

        Mockito.`when`(service.ajouter(gabaritProduit, codeUtilisateur)).thenReturn(gabaritProduit)

        mockMvc.perform(post("/gabaritproduits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(gabaritProduit)))
                .andExpect(status().isCreated)
    }

    @Test
    fun `Étant donnée un GabaritProduit avec le code 3, lorsqu'on essaie de supprimer avec la requête DELETE le GabaritProduit avec le code 3, on obtient le code 200`() {
        val codeUtilisateur = "testUser"

        Mockito.doNothing().`when`(service).supprimer(3, codeUtilisateur)

        mockMvc.perform(delete("/gabaritproduit/3"))
                .andExpect(status().isOk)
    }

    @Test
    fun `Étant donnée un GabaritProduit avec le code 3, lorsqu'on essaie de modifier un attribut avec la requête PUT, on obtient le code 200`() {
        val updatedGabaritProduit = créationÉchantillonGabaritProduit(3, "NomModifié", "DescriptionModifiée", "CatégorieModifiée")
        val codeUtilisateur = "testUser"

        Mockito.`when`(service.modifier(3, updatedGabaritProduit, codeUtilisateur)).thenReturn(true)

        mockMvc.perform(put("/gabaritproduit/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedGabaritProduit)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.nom").value("NomModifié"))
    }

    @Test
    fun `Étant donnée un GabaritProduit avec le code 4 qui n'existe pas, lorsqu'on exécute une requête PUT afin de modifier un attribut, on obtient alors un code d'erreur 404`() {
        val updatedGabaritProduit = créationÉchantillonGabaritProduit(4, "NomInexistant", "DescriptionInexistante", "CatégorieInexistante")
        val codeUtilisateur = "testUser" // Replace this with a valid code_util value or create a mock

        Mockito.`when`(service.modifier(4, updatedGabaritProduit, codeUtilisateur))
                .thenThrow(ExceptionRessourceIntrouvable("Le gabarit de code 4 est introuvable"))

        mockMvc.perform(put("/gabaritproduit/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedGabaritProduit)))
                .andExpect(status().isNotFound)
    }

 */
}
