package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.ItemsPanier
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Services.CommandeService
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
class CommandeControleurTest {

    @MockBean
    lateinit var service: CommandeService

    @Autowired
    private lateinit var mockMvc: MockMvc

    val commande: Commande = Commande(3,1,2, mutableListOf<ItemsPanier>(
            ItemsPanier(Produit(1, "patate", Date(), 10, 2.50), 2),
            ItemsPanier(Produit(2, "choux", Date(), 10, 2.50), 2),
            ItemsPanier(Produit(4, "onion", Date(), 10, 2.50), 2)
    ))

    @Test
    fun `Étant donnée la commande avec le code 3, lorsqu'on éffectue une requète GET avec le id 3 alors on obtient une commande dans un format JSON avec le id 3 et un code 200 `(){
        Mockito.`when`(service.chercherParCode(3)).thenReturn(commande)

        mockMvc.perform(get("/commande/3"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCommande").value(3))
    }

    @Test
    fun `Étant donnée la commande avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`(){
        Mockito.`when`(service.chercherParCode(4)).thenReturn(null)

        mockMvc.perform(get("/commande/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andExpect { résultat ->
                    assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("La commande avec le code 4 est introuvable", résultat.resolvedException?.message)
                }
    }
    @Test
    fun `Étant donnée une commande avec le code 4, lorsqu'on ajoute une commande à l'épicerie avec le code 1 l'aide d'une requète POST on obtient le code 201`(){
        TODO()
    }

    @Test
    fun `Étant donnée une commnde avec le code 3 qui existe déjà, lorsqu'on exécute une requête POST, alors on obtient un code d'erreur 409(conflit)`(){
        TODO()
    }

    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de supprimer avec la requête DELETE la commande avec le code 3, on obtient le code 200`(){
        TODO()
    }

    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de modifier un attribut avec la requête PUT, on obtient le code 200`(){
        TODO()
    }

    @Test
    fun `Étant donnée une commande avec le code 4 qui n'existe pas, lorsqu'on exécute un requête PUT afin de modifier un attribut on obtient alors un code d'erreur 404`(){
        TODO()
    }


}
