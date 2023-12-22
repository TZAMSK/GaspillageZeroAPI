package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Exceptions.LivraisonIntrouvableException
import com.GaspillageZeroAPI.Modèle.*
import com.GaspillageZeroAPI.Services.LivraisonService
import com.GaspillageZeroAPI.Services.ÉvaluationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.text.SimpleDateFormat
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class LivraisonControleurUtilisateursNonAuthentifiésTests {

        @MockBean
        lateinit var service: LivraisonService

        @MockBean
        lateinit var avis: ÉvaluationService

        @Autowired
        private lateinit var mockMvc: MockMvc

        @Autowired
        private lateinit var mapper: ObjectMapper

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse("2023-11-20 23:59:59")
        val livraison = SourceDonnées.livraison.get(1)
        val éval = Évaluation(1,1,3,"tres bon")

    /*  @Test
    fun `Étant donnée la livraison avec le code 1, lorsqu'on éffectue une requète GET alors on obtient une livraison  dans un format JSON avec le id 3 et un code 200 `(){
        val restaurant = Livraison("RF125", Commande(1,2,2,3),
            Utilisateur(1,"","","", Adresse("",3,"",))

    }*/
    @Test
    fun `Étant donnée un avis avec le code 19 qui n'existe , lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`(){


        Mockito.`when`(avis.chercherParCodeÉvaluation(19)).thenThrow(LivraisonIntrouvableException("L'avis du code 19 est introuvable"))

        mockMvc.perform(get("/evaluations/19"))
            .andExpect(status().isNotFound)

    }
    @Test
    fun `Étant donnée une livraison avec le code 1, lorsqu'on recherche un avis  l'aide d'une requète POST on obtient le code 201`(){
        Mockito.`when`(avis.chercherParCodeÉvaluation(1)).thenReturn(éval)

        mockMvc.perform(MockMvcRequestBuilders.get("/evaluations/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.idÉvaluation").value(1))
    }


    /*@Test
    fun `Étant donnée un avis avec le code 1, lorsqu'on essaie de supprimer avec une requête DELETE l'avis dont le code est 3, on obtient le code 200`() {
        Mockito.doNothing().`when`(avis).(3)

        mockMvc.perform(MockMvcRequestBuilders.delete("/produit/3"))
            .andExpect(status().isOk)
    }*/

    @Test
    fun `Étant donnée une livraison avec  l'id 3 qui n'existe pas, lorsqu'on éffectue un requête GET alors on obtient un code de retour 404`(){
        Mockito.`when`(avis.chercherParCodeÉvaluation(19)).thenThrow(LivraisonIntrouvableException("L'avis du code 19 est introuvable"))

        mockMvc.perform(get("/evaluations/19"))
            .andExpect(status().isNotFound)
    }

    @Test
    //@GetMapping("/evaluations/{code}")
    fun `Étant donné un avis dont le code est '9' et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche par code alors on obtient un code de retour 404 et le message d'erreur « L'avis avec le numéro '0009' n'est pas inscrit au service »`() {
        Mockito.`when`(avis.chercherParCodeÉvaluation(9)).thenThrow(LivraisonIntrouvableException("L'avis du code 9 est introuvable"))

        mockMvc.perform(get("/evaluations/9"))
            .andExpect(status().isNotFound)
    }

    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeLivraison}")
    fun `Étant donnée une livraison dont le code est '2', lorsqu'un utilisateur non-authentifié effectue une requête GET de recherche par code alors on obtient un code de retour 401`() {
        Mockito.`when`(service.obtenirLivraisonParCode(2)).thenReturn(livraison)

        mockMvc.perform(get("/utilisateur/3/commande/2/livraisons/2"))
            .andExpect(status().isUnauthorized)
    }

    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeLivraison}")
    fun `Étant donné une livraison dont le code est '10' et qui n'est pas inscrit au service lorsqu'on effectue une requête GET de recherche par code alors on obtient un code de retour 404` (){

        Mockito.`when`(service.obtenirLivraisonParCode(10)).thenReturn(null)

        mockMvc.perform(get("/utilisateur/10/commande/10/livraisons/10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                Assertions.assertEquals(
                    "La livraison avec le code 10 n'est pas inscrit au service.",
                    résultat.resolvedException?.message
                )
            }
    }

    @Test
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée un utilisateur non-authentifié et la livraison dont le code est '4' et qui n'est pas inscrit au service lorsque l'utilisateur effectue une requête POST pour l'ajouter alors il obtient un code de retour 401` (){
        val adresse = Adresse(4, "4444", "Rue Est", "Toronto", "ON", "M9B 3N4", "CA")
        val utilisateur = Utilisateur(4, "Vienneau", "Joël", "joelvienneau@gmail.com", adresse, "514 894-8268", "livreur", "auth0|657e62df17683158f971585b")
        val épicerie = Épicerie(1, adresse, utilisateur, "Metro", "metro@gmail.com", "514 231-6666", null)
        val produit = Produit(2, "tomate de la ferme Amarika", date, 25, 1.2, épicerie, GabaritProduit(1, "tomate", "tomate rouge", null, "Fruit&Légumes", épicerie))
        val commande = Commande(4, épicerie, utilisateur, mutableListOf(ItemsPanier(produit, 1)))
        val nouvelleLivraison = Livraison(4, commande, utilisateur, adresse)

        Mockito.`when`(service.ajouterLivraison(nouvelleLivraison)).thenReturn(nouvelleLivraison)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/utilisateur/4/commande/4/livraison").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(nouvelleLivraison)))
            .andExpect(status().isUnauthorized)
    }

    @Test
    //@PutMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée une livraison avec le code '2', lorsqu'on essaie de modifier un attribut avec la requête PUT, on obtient le code 200` (){
        val adresse = Adresse(3, "3333", "Rue Addison", "Toronto", "ON", "M5H 2N2", "CA")
        val utilisateur = Utilisateur(3, "Tabti", "Lyazid", "lyatabti@gmail.com", adresse, "514 894-8268", "client", "auth0|657e62829a0fa387ad493980")
        val épicerie = Épicerie(1, adresse, utilisateur, "Metro", "metro@gmail.com", "514 231-6666", null)
        val produit = Produit(2, "tomate de la ferme Amarika", date, 25, 1.2, épicerie, GabaritProduit(1, "tomate", "tomate rouge", null, "Fruit&Légumes", épicerie))
        val commande = Commande(2, épicerie, utilisateur, mutableListOf(ItemsPanier(produit, 1)))
        val updateLivraison = Livraison(2, commande, utilisateur, adresse)

        Mockito.`when`(service.modifierLivraison(2, updateLivraison)).thenReturn(updateLivraison)

        mockMvc.perform(MockMvcRequestBuilders.put("/utilisateur/3/commande/2/livraisons/2").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(updateLivraison)))
            .andExpect(status().isUnauthorized)
    }

    @Test
    //@DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée un utilisateur non-authentifié et le numéro de livraison dont le code est '2' et qui est inscrit au service lorsqu'on effectue une requête DELETE alors on obtient un code de retour 401` (){

        Mockito.doNothing().`when`(service).supprimerLivraison(2)

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/3/commande/2/livraisons/2").with(csrf()))
            .andExpect(status().isUnauthorized)
    }
}