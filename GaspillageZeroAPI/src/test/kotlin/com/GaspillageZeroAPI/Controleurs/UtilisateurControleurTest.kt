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
    fun `Étant donnée un utilisateur avec le id 3, lorsqu'on éffectue un requète GET alors on obtien un JSON qui contient un objet Utilisateur avec le ID 2 et le code 200`(){
        Mockito.`when`(service.chercherParCode(3)).thenReturn(utilisateur)

        mockMvc.perform(MockMvcRequestBuilders.get("/utilisateur/3"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(3))
    }

    @Test
    fun `Étant donnée un Utilisateur avec le id 11 qui n'existe pas, lorsqu'on éffectue un requête GET alors on obtient un code de retour 404`(){
        Mockito.`when`(service.chercherParCode(11)).thenReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/utilisateur/11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect { result ->
                    assertTrue(result.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("L'utilisateur avec le id 11 est introuvable", result.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 3 qui n'existe pas, lorsqu'on éffectue un requête POST alors on obtient un code 201`(){
        val utilisateurÀAjouter = SourceDonnées.utilisateurs.get(2)

        Mockito.`when`(service.ajouter(utilisateurÀAjouter)).thenReturn(utilisateurÀAjouter.copy(code = 9))

        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur")
                .with(csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("username"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(utilisateurÀAjouter)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.header().string("location", CoreMatchers.containsString("/utilisateur/9")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(9))
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 2 qui existe déjà, lorsqu'on exécute une requête POST alors on obtient un code d'erreur 409(conflit)`() {
        val utilisateurÀAjouter = SourceDonnées.utilisateurs[1]

        Mockito.`when`(service.ajouter(utilisateurÀAjouter))
                .thenThrow(ExceptionConflitRessourceExistante("Utilisateur existe déjà"))

        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(utilisateurÀAjouter)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict)
                .andExpect { result ->
                    assertTrue(result.resolvedException is ExceptionConflitRessourceExistante)
                    assertEquals("Utilisateur existe déjà", result.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un Utilsiateur avec le ID 2, lorsqu'on exécute un requête DELETE, on obtient le code 204`(){
        Mockito.doReturn(null).`when`(service).supprimer(2)

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/3"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
    }


    @Test
    fun `Étant donnée un Utilsiateur avec le ID 3 qui n'existe pas, lorsqu'on exécute une requête DELETE, on obtient le code 404`(){
        Mockito.doThrow(ExceptionRessourceIntrouvable("L'utilisateur avec le code 3 est introuvable"))
                .`when`(service).supprimer(3)

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/3"))
                .andExpect(MockMvcResultMatchers.status().isForbidden)
                .andExpect { result ->
                    assertTrue(result.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("L'utilisateur avec le ID 3 est introuvable", result.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 2, lorsqu'on exécute une requête PUT avec une objet Utilisateur en JSON, on obtient alors le code 202 pour accepted`(){
        val utilisateurModifie = SourceDonnées.utilisateurs.get(1)

        Mockito.`when`(service.modifier(2, "", utilisateurModifie)).thenReturn(utilisateurModifie)

        mockMvc.perform(MockMvcRequestBuilders.put("/utilisateur/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(utilisateurModifie)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomUtilisateur").value("Modified"))
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 3 qui n'existe pas, lorsqu'on exécute une requete PUT avec un objet Utilisateur en JSON, on obtient alors le code d'erreur 404`(){
        val utilisateurModifie = SourceDonnées.utilisateurs.get(2)

        Mockito.`when`(service.modifier(3, "", utilisateurModifie))
                .thenThrow(ExceptionRessourceIntrouvable("L'utilisateur avec le code 3 est introuvable"))

        mockMvc.perform(MockMvcRequestBuilders.put("/utilisateur/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(utilisateurModifie)))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect { résultat ->
                    assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("L'utilisateur avec le code 3 est introuvable", résultat.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 2, lorsqu'on exécute la requete avec sans l'objet JSON on obtient alors un code d'erreur 406`(){
        mockMvc.perform(MockMvcRequestBuilders.put("/utilisateur/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable)
    }

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
