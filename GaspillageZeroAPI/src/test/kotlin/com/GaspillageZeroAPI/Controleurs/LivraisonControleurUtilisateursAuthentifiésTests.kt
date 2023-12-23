package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Services.LivraisonService
import com.GaspillageZeroAPI.Services.ÉvaluationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class LivraisonControleurUtilisateursAuthentifiésTests {

    @MockBean
    lateinit var service: LivraisonService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    val livraison = Livraison(2, 2, 2, 2)

    @WithMockUser
    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeLivraison}")
    fun `Étant donné un utilisateur authentifié et une livraison dont le code est '2', lorsque l'utilisateur effectue une requête GET de recherche par code alors, il obtient un JSON qui contient une livraison dont le code est '2' et un code de retour 200`() {
        Mockito.`when`(service.obtenirLivraisonParCode(2)).thenReturn(livraison)

        mockMvc.perform(get("/utilisateur/2/commande/2/livraisons/2"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(2))
    }

    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de supprimer avec la requête DELETE la commande avec le code 3, on obtient le code 200`(){
        Mockito.doNothing().`when`(service).supprimerLivraison(3)

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("//utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().isOk)
    }

    @WithMockUser
    @Test
    fun `Étant donné un utilisateur authentifiéet une livraison dont le code est '3', lorsque l'utilisateur effectue une requête GET de recherche par code alors,  un code de retour 200`() {
        Mockito.`when`(service.obtenirLivraisonParCode(21)).thenReturn(livraison)

        mockMvc.perform(get("/utilisateur/21/commande/21/livraisons/21"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                Assertions.assertEquals(
                    "La livraison est introuvable avec le code 21 est introuvable",
                    résultat.resolvedException?.message
                )
            }
    }

    @Test
    fun `Étant donnée un utilisateur qui n'est pas authentifié, lorsque j'essaie d'ajouter une livraison avec une requête POST, on obtient alors un code de retour 401`(){
        Mockito.`when`(service.ajouterLivraison(SourceDonnées.livraison[1])).thenReturn(SourceDonnées.livraison[1])

        mockMvc.perform(
            MockMvcRequestBuilders.post("/livraison").with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(SourceDonnées.livraison[1])))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}