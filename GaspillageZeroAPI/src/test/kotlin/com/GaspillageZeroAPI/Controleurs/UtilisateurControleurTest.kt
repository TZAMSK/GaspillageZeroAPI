package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.DAO.SourceDonnées
import com.GaspillageZeroAPI.Exceptions.ExceptionAuthentification
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.*
import com.GaspillageZeroAPI.Services.UtilisateurService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class UtilisateurControleurTest{
    @MockBean
    lateinit var service: UtilisateurService

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    val utilisateur: Utilisateur = SourceDonnées.utilisateurs.get(2)

    @Test
    fun `Étant donné un utilisateur tentant de modifier ses informations sans une authentification valide, lorsqu'une requête PUT est effectuée, on obtient alors un code d'erreur 403`() {
        val utilisateurModifie = SourceDonnées.utilisateurs.get(1)
        val idUtilisateur = 2

        Mockito.`when`(service.validerUtilisateur(idUtilisateur, "incorrect_token")).thenReturn(false)

        mockMvc.perform(
                put("/utilisateur/$idUtilisateur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(utilisateurModifie))
                        .header("Authorization", "Bearer incorrect_token")
        )
                .andExpect(MockMvcResultMatchers.status().isForbidden)
                .andExpect { result ->
                    assertTrue(result.resolvedException is ExceptionAuthentification)
                    assertEquals("L'utilisateur doit être correctement authentifié pour pouvoir effectuer cette opération.", result.resolvedException?.message)
                }
    }
}
