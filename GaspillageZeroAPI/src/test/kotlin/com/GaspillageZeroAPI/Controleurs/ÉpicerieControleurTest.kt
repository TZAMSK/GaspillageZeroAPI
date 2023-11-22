package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ÉpicerieIntrouvableException
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Services.ÉpicerieService
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
class ÉpicerieControleurTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: ÉpicerieService

    @Autowired
    private lateinit var mapper: ObjectMapper

    private fun créationÉchantillonProduit(id: Int, nom: String, dateExpiration: Date, quantite: Int, prix: Double): Produit {
        return Produit(id, nom, dateExpiration, quantite, prix)
    }

    private fun créationÉchantillonGabaritProduit(id: Int, nom: String, description: String, categorie: String): GabaritProduit {
        val produits = listOf(
            créationÉchantillonProduit(1, "Produit1", Date(), 10, 2.5),
            créationÉchantillonProduit(2, "Produit2", Date(), 20, 5.0)
        )
        return GabaritProduit(id, nom, description, null, categorie, produits)
    }

    private fun créationÉchantillonEpicerie(id: Int, nom: String, courriel: String, telephone: String): Épicerie {
        val produits = listOf(
            créationÉchantillonProduit(3, "Produit3", Date(), 30, 7.5),
            créationÉchantillonProduit(4, "Produit4", Date(), 40, 10.0)
        )
        val gabaritProduits = listOf(
            créationÉchantillonGabaritProduit(5, "Gabarit1", "Description1", "Catégorie1"),
            créationÉchantillonGabaritProduit(6, "Gabarit2", "Description2", "Catégorie2")
        )
        return Épicerie(id, nom, courriel, telephone, produits, gabaritProduits)
    }

    @Test
    fun `Étant donnée une Épicerie avec le code 3, lorsqu'on éffectue une requète GET alors on obtient une Épicerie dans un format JSON avec le id 3 et un code 200 `() {
        val épicerie = créationÉchantillonEpicerie(3, "Épicerie3", "contact@epicerie3.com", "123-456-7890")
        Mockito.`when`(service.chercherParCode(3)).thenReturn(épicerie)

        mockMvc.perform(get("/epicerie/3"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.idÉpicerie").value(3))
            .andExpect(jsonPath("$.nom").value("Épicerie3"))
            .andExpect(jsonPath("$.courriel").value("contact@epicerie3.com"))
    }

    @Test
    fun `Étant donnée une Épicerie avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`() {
        Mockito.`when`(service.chercherParCode(4)).thenThrow(ÉpicerieIntrouvableException::class.java)

        mockMvc.perform(get("/epicerie/4"))
            .andExpect(status().isNotFound)
    }
}
