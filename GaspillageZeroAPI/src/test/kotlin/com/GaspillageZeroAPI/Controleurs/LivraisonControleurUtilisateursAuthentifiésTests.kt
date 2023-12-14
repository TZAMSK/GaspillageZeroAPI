package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Services.LivraisonService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class LivraisonControleurUtilisateursAuthentifiésTests {

    @MockBean
    lateinit var service: LivraisonService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    val livraison = Livraison(2, 2, 2, 2, null)

    @WithMockUser
    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons")
    fun `Étant donné un utilisateur authentifié et des livraisons inscrits au service lorsque l'utilisateur effectue une requête GET pour obtenir la liste de livraisons inscrits alors, il obtient la liste de livraisons et un code de retour 200` (){

        val liste_de_livraisons = listOf(Livraison(1,1,1,1, "gaston"), Livraison(2,2,2,2, "michel"))
        Mockito.`when`(service.obtenirLivraisons()).thenReturn(liste_de_livraisons)

        mockMvc.perform(get("/utilisateur/1/commande/1/livraisons"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].code").value(1))
    }

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
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée un utilisateur authentifié et une livraison avec le code '3', lorsqu'on inscrit une livraison au service avec le code '3' à l'aide d'une requête POST, on obtient un code de retour 201` (){

        val nouvelleLivraison = Livraison(3, 3, 3, 3, null)
        Mockito.`when`(service.ajouterLivraison(nouvelleLivraison)).thenReturn(nouvelleLivraison)

        mockMvc.perform(post("/utilisateur/3/commande/3/livraison")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(nouvelleLivraison)))
            .andExpect(status().isCreated)
            .andExpect(
                header().string("Location", CoreMatchers.containsString("/utilisateur/3/commande/3/livraisons/3")))
    }

    @WithMockUser
    @Test
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée un utilisateur authentifié et une livraison avec le code '3', lorsqu'on inscrit une livraison au service avec le code '3' à l'aide d'une requête POST et qu'on oublie le champ 'adresse_id', on obtient un code de retour 400` (){

        val nouvelleLivraison = """
                {
                    "code": 3,
                    "commande_code": 3,
                    "utilisateur_code": 3
                }
            """.trimIndent()

        mockMvc.perform(post("/utilisateur/3/commande/3/livraison")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(nouvelleLivraison)))
            .andExpect(status().isBadRequest)
    }

    @WithMockUser
    @Test
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée un utilisateur authentifié et une livraison dont le code est '2' qui existe déjà lorsque l'utilisateur effectue une requête POST pour l'ajouter alors il obtient un code de retour 409 et le message d'erreur « La livraison avec le numéro de code '2' est déjà inscrit au service » ` (){

        val livraisonExistante = Livraison(2,2,2,2, null)

        Mockito.`when`(service.obtenirLivraisonExistanteParCode(2)).thenThrow(ExceptionConflitRessourceExistante("La livraison avec le numéro de code 2 est déjà inscrit au service."))

        mockMvc.perform(post("/utilisateur/2/commande/2/livraison")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(livraisonExistante)))
            .andExpect(status().isConflict)
            .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is ExceptionConflitRessourceExistante)
                Assertions.assertEquals(
                    "La livraison avec le numéro de code 2 est déjà inscrit au service.",
                    résultat.resolvedException?.message
                )
            }
    }

    @WithMockUser(username = "anonyme")
    @Test
    //@DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée le numéro de livraison dont le code est '2' et un utilisateur 'Anonyme' authentifié lorsqu'il effectue une requête DELETE alors il obtient un code de retour 403` (){

        Mockito.doNothing().`when`(service).supprimerLivraison(2, "anonyme")

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/2/commande/2/livraisons/2"))
             .andExpect(status().isForbidden)
    }

    @WithMockUser(username = "gaston")
    @Test
    //@DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée le numéro de livraison dont le code est '1' et un utilisateur nommé 'Gaston' est authentifié lorsqu'il effectue une requête DELETE alors il obtient un code de retour 204` (){

        Mockito.doNothing().`when`(service).supprimerLivraison(1, "gaston")

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/1/commande/1/livraisons/1"))
            .andExpect(status().isNoContent)
    }
}