package com.GaspillageZeroAPI.Controleurs

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
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
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

    val utilisateur: Utilisateur = Utilisateur(2, "Africa", "Toto", "toto@afri.ca", Adresse(3,"123", "Saint-chose", "Québec", "B1B 1B1", "Canada"),"(123)456-7890")

    @Test
    fun `Étant donnée un utilisateur avec le id 2, lorsqu'on éffectue un requète GET alors on obtien un JSON qui contient un objet Utilisateur avec le ID 2 et le code 200`(){
        Mockito.`when`(service.chercherParCode(3)).thenReturn(utilisateur)

        mockMvc.perform(MockMvcRequestBuilders.get("/utilisateur/2"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idCommande").value(3))
    }

    @Test
    fun `Étant donnée un Utilisateur avec le id 3 qui n'existe pas, lorsqu'on éffectue un requête GET alors on obtient un code de retour 404`(){
        Mockito.`when`(service.chercherParCode(4)).thenReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/utilisateur/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect { résultat ->
                    assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("L'utilisateur avec le code 3 est introuvable", résultat.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 3 qui n'existe pas, lorsqu'on éffectue un requête POST alors on obtient un code 201`(){
        val utilisateurÀAjouter = Utilisateur(9, "Wilson", "Ken", "kenwilson@gmail.com",
                Adresse(123, "12345", "Place de Gaspésie", "Montréal", "Québec", "H4N 0F2", "Canada"), "514 618-2847", mutableListOf(
                Utilisateur_Rôle(9, "client", Date())
        ))

        Mockito.`when`(service.ajouter(utilisateurÀAjouter)).thenReturn(utilisateurÀAjouter)

        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(utilisateurÀAjouter)))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.header().string("location", CoreMatchers.containsString("/utilisateur/9")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idUtilisateur").value(9))
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 2 qui existe déjà, lorsqu'on exécute une requête POST alors on obtient un code d'erreur 409(conlit)`(){
        val utilisateurÀAjouter = Utilisateur(9, "Wilson", "Ken", "kenwilson@gmail.com",
                Adresse(123, "12345", "Place de Gaspésie", "Montréal", "Québec", "H4N 0F2", "Canada"), "514 618-2847", mutableListOf(
                Utilisateur_Rôle(9, "client", Date())
        ))

        Mockito.`when`(service.ajouter(utilisateurÀAjouter)).thenReturn(utilisateurÀAjouter)

        mockMvc.perform(MockMvcRequestBuilders.post("/utilisateur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(utilisateurÀAjouter)))
                .andExpect(MockMvcResultMatchers.status().isConflict)
                .andExpect {résultat ->
                    assertTrue(résultat.resolvedException is ExceptionConflitRessourceExistante)
                    assertEquals("La ressource avec ce id ${utilisateurÀAjouter.idUtilisateur} existe déjà dans la base de données", résultat.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un Utilsiateur avec le ID 2, lorsqu'on exécute un requête DELETE, on obtient le code 200`(){
        Mockito.doNothing().`when`(service).supprimer(2)

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/3"))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }


    @Test
    fun `Étant donnée un Utilsiateur avec le ID 3 qui n'existe pas, lorsqu'on exécute une requête DELETE, on obtient le code 404`(){
        Mockito.doThrow(ExceptionRessourceIntrouvable("L'utilisateur avec le code 3 est introuvable"))
                .`when`(service).supprimer(3)

        mockMvc.perform(MockMvcRequestBuilders.delete("/utilisateur/3"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect { résultat ->
                    assertTrue(résultat.resolvedException is ExceptionRessourceIntrouvable)
                    assertEquals("L'utilisateur avec le code 3 est introuvable", résultat.resolvedException?.message)
                }
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 2, lorsqu'on exécute une requête PUT avec une objet Utilisateur en JSON, on obtient alors le code 202 pour accepted`(){
        val utilisateurModifie = Utilisateur(2, "Modified", "Name", "modified@gmail.com",
                Adresse(1, "123", "Street", "City", "Province", "PostalCode", "Country"), "123-456-7890")

        Mockito.`when`(service.modifier(2, utilisateurModifie)).thenReturn(utilisateurModifie)

        mockMvc.perform(MockMvcRequestBuilders.put("/utilisateur/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(utilisateurModifie)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomUtilisateur").value("Modified"))
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 3 qui n'existe pas, lorsqu'on exécute une requete PUT avec un objet Utilisateur en JSON, on obtient alors le code d'erreur 404`(){
        val utilisateurModifie = Utilisateur(3, "Modified", "Name", "modified@gmail.com",
                Adresse(1, "123", "Street", "City", "Province", "PostalCode", "Country"), "123-456-7890")

        Mockito.`when`(service.modifier(3, utilisateurModifie))
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
}
