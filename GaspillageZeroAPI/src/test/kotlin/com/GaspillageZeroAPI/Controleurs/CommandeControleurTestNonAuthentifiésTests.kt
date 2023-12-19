package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Services.CommandeService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class CommandeControleurTestNonAuthentifiésTests {
    @MockBean
    lateinit var service: CommandeService

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Étant donnée la commande avec le code 3, lorsqu'on éffectue une requète GET avec le id 3 alors on obtient une commande dans un format JSON avec le id 3 et un code 200 `(){
        Mockito.`when`(service.chercherParCode(3)).thenReturn(SourceDonnées.commandes.get(2))

        mockMvc.perform(MockMvcRequestBuilders.get("/commande/3"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCommande").value(3))
    }

    @Test
    fun `Étant donnée la commande avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`(){
        Mockito.`when`(service.chercherParCode(4)).thenReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/commande/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect { résultat ->
                    Assertions.assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                    Assertions.assertEquals("La commande avec le code 4 est introuvable", résultat.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un utilisateur qui n'est pas authentifié, lorsque j'essaie d'ajouter une commande avec une requête POST, on obtient alors un code de retour 401`(){
        Mockito.`when`(service.ajouter(SourceDonnées.commandes[0], null)).thenReturn(SourceDonnées.commandes[0])

        mockMvc.perform(
                MockMvcRequestBuilders.post("/commande").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(SourceDonnées.commandes[0])))
                        .andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}