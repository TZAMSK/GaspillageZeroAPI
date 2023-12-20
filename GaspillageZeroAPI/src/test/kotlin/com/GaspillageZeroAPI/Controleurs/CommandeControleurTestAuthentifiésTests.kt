package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Services.CommandeService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matchers
import org.hamcrest.core.IsEqual.equalTo
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.security.Principal
import java.util.*


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
    fun `Etant donnee la commande avec le code 3, lorsqu'on effectue une requête GET avec le id 3, alors on obtient une commande dans un format JSON avec le id 3 et un code 200`() {
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
    fun `Given a command with code 4, when adding a command to the grocery store with code 1 using a POST request, then receive status code 201`() {
        // Given
        val commandeToAdd = SourceDonnées.commandes[3]

        Mockito.`when`(service.ajouter(commandeToAdd, null)).thenReturn(commandeToAdd)

        mockMvc.perform(
                MockMvcRequestBuilders.post("/commande")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commandeToAdd))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(header().string("location", containsString("/commande/4")))
                .andExpect(jsonPath("$.idCommande").value("4"))
    }

    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 4, lorsqu'on ajoute une commande à l'épicerie avec le code 1 l'aide d'une requète POST on obtient le code 201`() {
        val commandeToAdd = SourceDonnées.commandes[3]

        Mockito.`when`(service.ajouter(commandeToAdd, null)).thenReturn(commandeToAdd)

        mockMvc.perform(
                MockMvcRequestBuilders.post("/commande")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(commandeToAdd))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(header().string("location", containsString("/commande/4")))
                .andExpect(jsonPath("$.idCommande").value("4"))
    }

    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 3, lorsqu'on essaie de supprimer avec la requête DELETE la commande avec le code 3, on obtient le code 204`() {
        Mockito.doNothing().`when`(service).supprimer(3, "")

        mockMvc.perform(MockMvcRequestBuilders.delete("/commande/3").with(csrf()))
                .andExpect(status().isNoContent)
    }

    @WithMockUser(username = "Samuel", roles = ["client"])
    @Test
    fun `Given a command with code 3, when trying to modify an attribute with the PUT request, then receive status code 200`() {
        val updatedCommand = SourceDonnées.commandes[2]
        val principal = mock(Principal::class.java)
        given(principal.name).willReturn("Samuel")

        doNothing().`when`(service).modifier(3, updatedCommand, principal)

        mockMvc.perform(
                put("/commande/3")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedCommand))
        )
                .andExpect(status().isOk)
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCommande", equalTo(3)))
                .andExpect(jsonPath("$.itemsPanier[0].produit.nomProduit").value("updated-potato"))
                .andExpect(jsonPath("$.itemsPanier[1].produit.nomProduit").value("updated-cabbage"))
    }


    @WithMockUser
    @Test
    fun `Étant donnée une commande avec le code 4 qui n'existe pas, lorsqu'on exécute un requête PUT afin de modifier un attribut on obtient alors un code d'erreur 404`() {
        val updatedCommand = SourceDonnées.commandes[2]

        val principal = UsernamePasswordAuthenticationToken(
                User("Alikhan", "", emptyList()),
                null,
                Collections.emptyList()
        )

        SecurityContextHolder.getContext().authentication = principal

        Mockito.`when`(service.modifier(4, updatedCommand, principal))
                .thenThrow(ExceptionRessourceIntrouvable("La commande avec le code 4 est introuvable"))
        
        mockMvc.perform(
                MockMvcRequestBuilders.put("/commande/4")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedCommand))
        )
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
                        .content(mapper.writeValueAsString(nouvelleCommand))
        )
                .andExpect(status().isCreated) // Adjusted this line
                .andExpect(header().string("location", Matchers.containsString("/commande/")))
    }
}
