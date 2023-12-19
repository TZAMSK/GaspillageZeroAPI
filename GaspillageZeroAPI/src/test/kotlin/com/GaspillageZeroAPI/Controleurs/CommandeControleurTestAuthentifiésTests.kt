package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
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
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest
@AutoConfigureMockMvc
class CommandeControleurTestAuthentifiésTests {

    @MockBean
    lateinit var service: CommandeService

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Étant donnée la commande avec le code 3, lorsqu'on éffectue une requète GET avec le id 3 alors on obtient une commande dans un format JSON avec le id 3 et un code 200 `(){
            Mockito.`when`(service.chercherParCode(3)).thenReturn(SourceDonnées.commandes.get(2))

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
    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 4, lorsqu'on ajoute une commande à l'épicerie avec le code 1 l'aide d'une requète POST on obtient le code 201`(){
        val commandeÀAjouter = SourceDonnées.commandes.get(3)

        Mockito.`when`(service.ajouter(commandeÀAjouter, null)).thenReturn(commandeÀAjouter)

        mockMvc.perform(MockMvcRequestBuilders.post("/commande").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandeÀAjouter)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(header().string("location", containsString("/commande/4")))
                .andExpect(jsonPath("$.idCommande").value("4"))
    }

    @WithMockUser
    @Test
    fun `Étant donnée une commnde avec le code 3 qui existe déjà, lorsqu'on exécute une requête POST, alors on obtient un code d'erreur 409(conflit)`(){
        val commandeÀAjouter = SourceDonnées.commandes.get(2)


        Mockito.`when`(service.ajouter(commandeÀAjouter, null)).thenThrow(ExceptionConflitRessourceExistante("La ressource avec ce id ${commandeÀAjouter.idCommande} existe déjà dans la base de données"))

        mockMvc.perform(post("/commande").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(commandeÀAjouter)))
                .andExpect(status().isConflict)
                .andExpect {résultat ->
                    assertTrue(résultat.resolvedException is ExceptionConflitRessourceExistante)
                    assertEquals("La ressource avec ce id ${commandeÀAjouter.idCommande} existe déjà dans la base de données", résultat.resolvedException?.message)
                }
    }

    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de supprimer avec la requête DELETE la commande avec le code 3, on obtient le code 200`(){
        Mockito.doNothing().`when`(service).supprimer(3, "")

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/commande/3").with(csrf()))
                .andExpect(status().isOk)
    }

    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de modifier un attribut avec la requête PUT, on obtient le code 200`(){
        val updatedCommand = SourceDonnées.commandes.get(2)

        Mockito.`when`(service.modifier(3, updatedCommand)).thenReturn(updatedCommand)

        mockMvc.perform(MockMvcRequestBuilders.put("/commande/3").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedCommand)))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.idCommande").value(3))
                .andExpect(jsonPath("$.itemsPanier[0].produit.nomProduit").value("updated-potato"))
                .andExpect(jsonPath("$.itemsPanier[1].produit.nomProduit").value("updated-cabbage"))
    }

    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 4 qui n'existe pas, lorsqu'on exécute un requête PUT afin de modifier un attribut on obtient alors un code d'erreur 404`(){
        val updatedCommand = SourceDonnées.commandes.get(2)

        Mockito.`when`(service.modifier(4, updatedCommand)).thenThrow(ExceptionRessourceIntrouvable("La commande avec le code 4 est introuvable"))

        mockMvc.perform(MockMvcRequestBuilders.put("/commande/4").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedCommand)))
                .andExpect(status().isNotFound)
                .andExpect { result ->
                    assertTrue(result.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("La commande avec le code 4 est introuvable", result.resolvedException?.message)
                }
    }

    @WithMockUser
    @Test
    fun `Étant donnée une nouvelle commande, lorsqu'elle est ajoutée avec une requête valide, alors retourner un code 201 Created`() {
        val nouvelleCommand = Commande(
                idCommande = null,
                épicerie = null,
                utilisateur = null,
                panier = mutableListOf()
        )

        Mockito.`when`(service.ajouter(nouvelleCommand, null)).thenReturn(nouvelleCommand)

        mockMvc.perform(
                MockMvcRequestBuilders.post("/commande").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(nouvelleCommand)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.header().exists("location"))
    }
}
