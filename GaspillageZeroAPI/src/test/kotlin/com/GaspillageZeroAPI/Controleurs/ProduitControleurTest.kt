package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Services.ProduitService
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
class ProduitControleurTest {
/*
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: ProduitService

    @Autowired
    private lateinit var mapper: ObjectMapper

    private fun créationÉchantillonProduit(id: Int, nom: String, dateExpiration: Date, quantite: Int, prix: Double, idÉpicerie: Int, idGabaritProduit: Int): Produit {
        return Produit(id, nom, dateExpiration, quantite, prix, idÉpicerie, idGabaritProduit)
    }

    @Test
    fun `Étant donnée le Produit avec le code 3, lorsqu'on éffectue une requète GET alors on obtient un Produit dans un format JSON avec le id 3 et un code 200 `() {
        val produit = créationÉchantillonProduit(3, "Produit3", Date(), 10, 5.0, 1, 1)
        Mockito.`when`(service.chercherParCode(3)).thenReturn(produit)

        mockMvc.perform(get("/produit/3"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.idProduit").value(3))
            .andExpect(jsonPath("$.nom").value("Produit3"))
            .andExpect(jsonPath("$.quantité").value(10))
            .andExpect(jsonPath("$.prix").value(5.0))
    }

    @Test
    fun `Étant donnée le Produit avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`() {
        Mockito.`when`(service.chercherParCode(4)).thenThrow(ProduitIntrouvableException::class.java)

        mockMvc.perform(get("/produit/4"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `Étant donnée un Produit avec le code 4, lorsqu'on ajoute un Produit à l'épicerie avec le code 1 l'aide d'une requète POST on obtient le code 201`() {
        val nouveauProduit = créationÉchantillonProduit(4, "NouveauProduit", Date(), 15, 10.0, 1, 1)
        Mockito.`when`(service.ajouter(nouveauProduit)).thenReturn(nouveauProduit)

        mockMvc.perform(post("/produit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(nouveauProduit)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.idProduit").value(4))
    }

    @Test
    fun `Étant donnée un Produit avec le code 3 qui existe déjà, lorsqu'on exécute une requête POST, alors on obtient un code d'erreur 409(conflit)`() {
        val produitExistant = créationÉchantillonProduit(3, "ProduitExistant", Date(), 10, 5.0, 1, 1)
        Mockito.`when`(service.ajouter(produitExistant)).thenThrow(RuntimeException("Conflit"))

        mockMvc.perform(post("/produit")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(produitExistant)))
            .andExpect(status().isConflict)
    }

    @Test
    fun `Étant donnée un Produit avec le code 3, lorsqu'on essaie de supprimer avec la requête DELETE le Produit avec le code 3, on obtient le code 200`() {
        Mockito.doNothing().`when`(service).supprimer(3)

        mockMvc.perform(delete("/produit/3"))
            .andExpect(status().isOk)
    }

    @Test
    fun `Étant donnée un Produit avec le code 3, lorsqu'on essaie de modifier un attribut avec la requête PUT, on obtient le code 200`() {
        val produitModifié = créationÉchantillonProduit(3, "ProduitModifié", Date(), 20, 10.0, 1, 1)
        Mockito.`when`(service.modifier(3, produitModifié)).thenReturn(produitModifié)

        mockMvc.perform(put("/produit/3")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(produitModifié)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.nom").value("ProduitModifié"))
    }

    @Test
    fun `Étant donnée un Produit avec le code 4 qui n'existe pas, lorsqu'on exécute un requête PUT afin de modifier un attribut on obtient alors un code d'erreur 404`() {
        val produitInexistant = créationÉchantillonProduit(4, "ProduitInexistant", Date(), 30, 15.0, 1, 1)
        Mockito.`when`(service.modifier(4, produitInexistant)).thenThrow(ProduitIntrouvableException::class.java)

        mockMvc.perform(put("/produit/4")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(produitInexistant)))
            .andExpect(status().isNotFound)
    }

 */
}
