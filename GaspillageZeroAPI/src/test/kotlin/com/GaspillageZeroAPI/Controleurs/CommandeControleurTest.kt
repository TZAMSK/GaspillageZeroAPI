package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.ItemsPanier
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Services.CommandeService
import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.hamcrest.CoreMatchers.containsString
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders


@SpringBootTest
@AutoConfigureMockMvc
class CommandeControleurTest {

    @MockBean
    lateinit var service: CommandeService

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    val commande: Commande = Commande(3,1,2, mutableListOf<ItemsPanier>(
            ItemsPanier(1, 2),
            ItemsPanier(2, 2),
            ItemsPanier(4, 3),
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
        val commandeÀAjouter = Commande(11, 2, 1, mutableListOf<ItemsPanier>(
                ItemsPanier(5, 3),
                ItemsPanier(22, 2),
                ItemsPanier(87, 4),
        ))

        Mockito.`when`(service.ajouter(commandeÀAjouter)).thenReturn(commandeÀAjouter)

        mockMvc.perform(post("/commande")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandeÀAjouter)))
                .andExpect(status().isCreated)
                .andExpect(header().string("location", containsString("/commande/11")))
                .andExpect(jsonPath("$.idCommande").value("11"))
    }

    @Test
    fun `Étant donnée une commnde avec le code 3 qui existe déjà, lorsqu'on exécute une requête POST, alors on obtient un code d'erreur 409(conflit)`(){
        val commandeÀAjouter = Commande(11, 2, 1, mutableListOf<ItemsPanier>(
                ItemsPanier(5, 3),
                ItemsPanier(22, 2),
                ItemsPanier(87, 4),
        ))

        Mockito.`when`(service.ajouter(commandeÀAjouter)).thenThrow(ExceptionConflitRessourceExistante("La ressource avec ce id ${commandeÀAjouter.idCommande} existe déjà dans la base de données"))

        mockMvc.perform(post("/commande")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandeÀAjouter)))
                .andExpect(status().isConflict)
                .andExpect {résultat ->
                    assertTrue(résultat.resolvedException is ExceptionConflitRessourceExistante)
                    assertEquals("La ressource avec ce id ${commandeÀAjouter.idCommande} existe déjà dans la base de données", résultat.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de supprimer avec la requête DELETE la commande avec le code 3, on obtient le code 200`(){
        Mockito.doNothing().`when`(service).supprimer(3)

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/commande/3"))
                .andExpect(status().isOk)
    }

    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de modifier un attribut avec la requête PUT, on obtient le code 200`(){
        val updatedCommand = Commande(3, 1, 2, mutableListOf(
                ItemsPanier(1, 3),
                ItemsPanier(2, 3)
        ))
        Mockito.`when`(service.modifier(3, updatedCommand)).thenReturn(updatedCommand)

        mockMvc.perform(MockMvcRequestBuilders.put("/commande/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedCommand)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.idCommande").value(3))
                .andExpect(jsonPath("$.itemsPanier[0].produit.nomProduit").value("updated-potato"))
                .andExpect(jsonPath("$.itemsPanier[1].produit.nomProduit").value("updated-cabbage"))
    }

    @Test
    fun `Étant donnée une commande avec le code 4 qui n'existe pas, lorsqu'on exécute un requête PUT afin de modifier un attribut on obtient alors un code d'erreur 404`(){
        val updatedCommand = Commande(4, 1, 2, mutableListOf(
                ItemsPanier(1, 3),
                ItemsPanier(2, 3)
        ))
        Mockito.`when`(service.modifier(4, updatedCommand)).thenThrow(ExceptionRessourceIntrouvable("La commande avec le code 4 est introuvable"))

        mockMvc.perform(MockMvcRequestBuilders.put("/commande/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedCommand)))
                .andExpect(status().isNotFound)
                .andExpect { result ->
                    assertTrue(result.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("La commande avec le code 4 est introuvable", result.resolvedException?.message)
                }
    }


}
