package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.*
import com.GaspillageZeroAPI.Services.LivraisonService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.text.SimpleDateFormat


@SpringBootTest
@AutoConfigureMockMvc
class LivraisonControleurUtilisateursAuthentifiésTests {

    @MockBean
    lateinit var service: LivraisonService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = dateFormat.parse("2023-11-20 23:59:59")
    val livraison = SourceDonnées.livraison.get(1)

    @WithMockUser
    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons")
    fun `Étant donné un utilisateur authentifié et des livraisons inscrits au service lorsque l'utilisateur effectue une requête GET pour obtenir la liste de livraisons inscrits alors, il obtient la liste de livraisons et un code de retour 200` (){

        Mockito.`when`(service.obtenirLivraisons()).thenReturn(SourceDonnées.livraison)

        mockMvc.perform(get("/utilisateur/1/commande/1/livraisons"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0]['code']").value(1))
    }

    @WithMockUser(username = "auth0|657e62829a0fa387ad493980")
    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeLivraison}")
    fun `Étant donné l'utilisateur 'Lyazid' authentifié et une livraison dont le code est '2', lorsque l'utilisateur effectue une requête GET de recherche par code alors, il obtient un JSON qui contient une livraison dont le code est '2' et un code de retour 200`() {

        Mockito.`when`(service.obtenirLivraisonParCode(2)).thenReturn(livraison)

        mockMvc.perform(get("/utilisateur/3/commande/2/livraisons/2"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @WithMockUser(username = "auth0|657e61dfd4eada6b02d17dd9")
    @Test
    //@GetMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{codeLivraison}")
    fun `Étant donné l'utilisateur 'Samuel' authentifié et une livraison dont le code est '2', lorsque l'utilisateur effectue une requête GET de recherche par code d'une autre livraison alors, il obtient un code de retour 403`() {

        Mockito.`when`(service.obtenirLivraisonParCode(2)).thenReturn(livraison)

        mockMvc.perform(get("/utilisateur/3/commande/2/livraisons/2"))
            .andExpect(status().isForbidden)
    }

    @WithMockUser
    @Test
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée un utilisateur authentifié et une livraison avec le code '4', lorsqu'on inscrit une livraison au service avec le code '4' à l'aide d'une requête POST, on obtient un code de retour 201` (){
        val adresse = Adresse(4, "4444", "Rue Est", "Toronto", "ON", "M9B 3N4", "CA")
        val utilisateur = Utilisateur(4, "Vienneau", "Joël", "joelvienneau@gmail.com", adresse, "514 894-8268", "livreur", "auth0|657e62df17683158f971585b")
        val épicerie = Épicerie(1, adresse, utilisateur, "Metro", "metro@gmail.com", "514 231-6666", null)
        val produit = Produit(2, "tomate de la ferme Amarika", date, 25, 1.2, épicerie, GabaritProduit(1, "tomate", "tomate rouge", null, "Fruit&Légumes", épicerie))
        val commande = Commande(4, épicerie, utilisateur, mutableListOf(ItemsPanier(produit, 1)))
        val nouvelleLivraison = Livraison(4, commande, utilisateur, adresse)
        Mockito.`when`(service.ajouterLivraison(nouvelleLivraison)).thenReturn(nouvelleLivraison)

        mockMvc.perform(post("/utilisateur/4/commande/4/livraison").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(nouvelleLivraison)))
            .andExpect(status().isCreated)
            .andExpect(
                header().string("Location", CoreMatchers.containsString("/utilisateur/4/commande/4/livraisons/4")))
    }

    @WithMockUser
    @Test
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée un utilisateur authentifié et une livraison avec le code '4', lorsqu'on inscrit une livraison au service avec le code '4' à l'aide d'une requête POST et qu'on laisse le champ 'idCommande' vide, on obtient un code de retour 400` (){

        val nouvelleLivraison = """
                {
                    {
                      "code": 4,
                      "commande": {
                            "idCommande": ,
                        "panier": [
                          {
                }
            """.trimIndent()

        mockMvc.perform(post("/utilisateur/4/commande/4/livraison").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(nouvelleLivraison)))
            .andExpect(status().isBadRequest)
    }

    @WithMockUser
    @Test
    //@PostMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraison")
    fun `Étant donnée un utilisateur authentifié et une livraison dont le code est '2' qui existe déjà lorsque l'utilisateur effectue une requête POST pour l'ajouter alors il obtient un code de retour 409 et le message d'erreur « La livraison avec le numéro de code '2' est déjà inscrit au service » ` (){
        val adresse = Adresse(3, "3333", "Rue Addison", "Toronto", "ON", "M5H 2N2", "CA")
        val utilisateur = Utilisateur(3, "Tabti", "Lyazid", "lyatabti@gmail.com", adresse, "514 894-8268", "client", "auth0|657e62829a0fa387ad493980")
        val épicerie = Épicerie(1, adresse, utilisateur, "Metro", "metro@gmail.com", "514 231-6666", null)
        val produit = Produit(2, "tomate de la ferme Amarika", date, 25, 1.2, épicerie, GabaritProduit(1, "tomate", "tomate rouge", null, "Fruit&Légumes", épicerie))
        val commande = Commande(2, épicerie, utilisateur, mutableListOf(ItemsPanier(produit, 1)))
        val livraisonExistante = Livraison(2, commande, utilisateur, adresse)
        Mockito.`when`(service.obtenirLivraisonExistanteParCode(2)).thenThrow(ExceptionConflitRessourceExistante("La livraison avec le numéro de code 2 est déjà inscrit au service."))

        mockMvc.perform(post("/utilisateur/2/commande/2/livraison").with(csrf())
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

    @WithMockUser(username = "auth0|657e62829a0fa387ad493980")
    @Test
    //@PutMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée l'utilisateur 'Lyazid' authentifié et la livraison dont le code est '2' et qui est inscrit au service et dont le nom du produit est 'tomate de la ferme Amarika' lorsque l'utilisateur effectue une requête PUT pour modifier le nom pour 'tomate de la ferme Rosemont' alors il obtient un code de retour 200` (){
        val adresse = Adresse(3, "3333", "Rue Addison", "Toronto", "ON", "M5H 2N2", "CA")
        val utilisateur = Utilisateur(3, "Tabti", "Lyazid", "lyatabti@gmail.com", adresse, "514 894-8268", "client", "auth0|657e62829a0fa387ad493980")
        val épicerie = Épicerie(1, adresse, utilisateur, "Metro", "metro@gmail.com", "514 231-6666", null)
        val produit = Produit(2, "tomate de la ferme Rosemont", date, 25, 1.2, épicerie, GabaritProduit(1, "tomate", "tomate rouge", null, "Fruit&Légumes", épicerie))
        val commande = Commande(2, épicerie, utilisateur, mutableListOf(ItemsPanier(produit, 1)))
        val updateLivraison = Livraison(2, commande, utilisateur, adresse)

        Mockito.`when`(service.modifierLivraison(2, updateLivraison)).thenReturn(null)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/utilisateur/3/commande/2/livraisons/2").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateLivraison)))
            .andExpect(status().isOk)
    }

    @WithMockUser(username = "auth0|657e6357b037b915578c1335")
    @Test
    //@PutMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée l'utilisateur 'Audric' authentifié et la livraison dont le code est '2' et qui est inscrit au service et dont le nom du produit est 'tomate de la ferme Amarika' lorsque l'utilisateur effectue une requête PUT pour modifier le nom pour 'tomate de la ferme Rosemont' alors il obtient un code de retour 403` (){
        val adresse = Adresse(3, "3333", "Rue Addison", "Toronto", "ON", "M5H 2N2", "CA")
        val utilisateur = Utilisateur(3, "Tabti", "Lyazid", "lyatabti@gmail.com", adresse, "514 894-8268", "client", "auth0|657e62829a0fa387ad493980")
        val épicerie = Épicerie(1, adresse, utilisateur, "Metro", "metro@gmail.com", "514 231-6666", null)
        val produit = Produit(2, "tomate de la ferme Rosemont", date, 25, 1.2, épicerie, GabaritProduit(1, "tomate", "tomate rouge", null, "Fruit&Légumes", épicerie))
        val commande = Commande(2, épicerie, utilisateur, mutableListOf(ItemsPanier(produit, 1)))
        val updateLivraison = Livraison(2, commande, utilisateur, adresse)

        Mockito.`when`(service.modifierLivraison(2, updateLivraison)).thenReturn(null)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/utilisateur/3/commande/2/livraisons/2").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateLivraison)))
            .andExpect(status().isForbidden)
    }

    @WithMockUser(username = "auth0|657e62829a0fa387ad493980")
    @Test
    //@PutMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée l'utilisateur 'Lyazid' authentifié et la livraison dont le code est '2' et qui n'est pas inscrit au service lorsque l'utilisateur effectue une requête PUT et que le champ 'idCommande' est manquant dans le JSON envoyé alors il obtient un code de retour 400` (){
        val nouvelleLivraison = """
                {
                    {
                      "code": 2,
                      "commande": {
                            "idCommande": ,
                        "panier": [
                          {
                }
            """.trimIndent()

        mockMvc.perform(
            MockMvcRequestBuilders.put("/utilisateur/3/commande/2/livraisons/2").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(nouvelleLivraison))
            .andExpect(status().isBadRequest)
    }

    @WithMockUser(username="auth0|657e62829a0fa387ad493980")
    @Test
    //@DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée le numéro de livraison dont le code est '3' et l'utilisateur 'Lyazid' authentifié lorsqu'il effectue une requête DELETE alors il obtient un code de retour 204` (){

        Mockito.doNothing().`when`(service).supprimerLivraison(2)

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/3/commande/2/livraisons/2").with(csrf()))
            .andExpect(status().isNoContent)
    }

    @WithMockUser(username = "auth0|657e624f9a0fa387ad49396e")
    @Test
    //@DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donnée le numéro de livraison dont le code est '3' et l'utilisateur 'Alikhan' authentifié lorsqu'il effectue une requête DELETE alors il obtient un code de retour 403` (){

        Mockito.`when`(service.supprimerLivraison(3)).thenThrow(DroitAccèsInsuffisantException("Seul l'utilisateur avec le code auth0|657e624f9a0fa387ad49396e peut supprimer cette livraison."))

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/4/commande/3/livraisons/3").with(csrf()))
             .andExpect(status().isForbidden)
             .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is DroitAccèsInsuffisantException)
                Assertions.assertEquals(
                    "Seul l'utilisateur avec le code auth0|657e624f9a0fa387ad49396e peut supprimer cette livraison.",
                    résultat.resolvedException?.message
                )
            }
    }

    @WithMockUser(username = "auth0|657e63284d56c64e93880850")
    @Test
    //@DeleteMapping("/utilisateur/{code_utilisateur}/commande/{idCommande}/livraisons/{code}")
    fun `Étant donné l'utilisateur 'Jean-Gabriel' authentifié et la livraison dont le code est '10' et qui n'est pas inscrit au service lorsque l'utilisateur effectue une requête DELETE alors il obtient un code de retour 404` (){

        Mockito.`when`(service.supprimerLivraison(10)).thenThrow(ExceptionRessourceIntrouvable("La livraison avec le code 10 n'est pas inscrit au service."))

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/10/commande/10/livraisons/10").with(csrf()))
            .andExpect(status().isNotFound)
            .andExpect { résultat ->
                Assertions.assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                Assertions.assertEquals(
                    "La livraison avec le code 10 n'est pas inscrit au service.",
                    résultat.resolvedException?.message
                )
            }
    }
}
