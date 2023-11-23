package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Services.LivraisonService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class LivraisonControleur {

    @MockBean
    lateinit var service: LivraisonService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    /*  @Test
    fun `Étant donnée la livraison avec le code 1, lorsqu'on éffectue une requète GET alors on obtient une livraison  dans un format JSON avec le id 3 et un code 200 `(){
        val restaurant = Livraison("RF125", Commande(1,2,2,3),
            Utilisateur(1,"","","", Adresse("",3,"",))

    }*/
    /*@Test
    fun `Étant donnée une livraison avec le code 2 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`(){
        Mockito.`when`(service.obtenirLivraisonParCode(26)).thenReturn(null)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/livraison/26")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is RessourceInexistanteException)
                Assertions.assertEquals(
                    "La livraison au code 26 n'est pas inscrit au service.",
                    résultat.resolvedException?.message
                )
            }
    }*/
    @Test
    fun `Étant donnée une commande avec le code 4, lorsqu'on ajoute une commande à une livraison avec le code 1 l'aide d'une requète POST on obtient le code 201`(){

    }

    @Test
    fun `Étant donnée une livraison avec  l'id 3 qui n'existe pas, lorsqu'on éffectue un requête GET alors on obtient un code de retour 404`(){
        TODO()
    }

    @Test
    //@GetMapping("/evaluations/{code}")
    fun `Étant donné un avis dont le code est '0009' et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche par code alors on obtient un code de retour 404 et le message d'erreur « L'avis avec le numéro '0009' n'est pas inscrit au service »`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    //@GetMapping("/evaluations/{code}")
    fun `Étant donné un avis dont le code est '0001' lorsqu'on effectue une requête GET de recherche par code alors on obtient un JSON qui contient un avis dont le code est '0001' et un code de retour 200`() {
        TODO("Méthode non-implémentée")
    }

    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeCommande}")
    fun `Étant donné une livraison dont le code est '1' et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche par code alors on obtient un code de retour 404` (){

        Mockito.`when`(service.obtenirLivraisonParCode(1)).thenReturn(null)

        mockMvc.perform(get("/utilisateur/1/commande/1/livraisons/1", "not_found")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

    @Test
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée une livraison dont le code est '3' et qui n'est pas inscrit au service lorsqu'on effectue une requête POST pour l'ajouter alors on obtient un JSON qui contient une livraison dont le code est '3' et un code de retour 201` (){

        val livraison = Livraison(3, 3, 3, 3)
        Mockito.`when`(service.ajouterLivraison(livraison)).thenReturn(3)

        mockMvc.perform(
            post("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison", 3,3)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(livraison)))
            .andExpect(status().isCreated)
    }

    @Test
    //@DeleteMapping("/livraisons/{code}")
    fun `Étant donnée le numéro de livraison dont le code est '04590' et qui est inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 204` (){

        Mockito.doNothing().`when`(service).supprimerLivraison(2)

        mockMvc.perform(delete("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}", 2,2,2))
            .andExpect(status().isNoContent)
    }
}