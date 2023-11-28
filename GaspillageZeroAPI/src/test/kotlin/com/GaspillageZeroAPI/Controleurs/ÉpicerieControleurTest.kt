package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ÉpicerieService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.sql.Blob

@SpringBootTest
@AutoConfigureMockMvc
class ÉpicerieControleurTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: ÉpicerieService

    @Autowired
    private lateinit var mapper: ObjectMapper

    private fun créationÉchantillonEpicerie(idÉpicerie: Int, idAdresse: Int, idUtilisateur: Int, nom: String, courriel: String, téléphone: String, logo: Blob?): Épicerie {
        return Épicerie(idÉpicerie, idAdresse, idUtilisateur, nom, courriel, téléphone, logo)
    }

    @Test
    fun `Étant donnée une Épicerie avec le code 4, lorsqu'on éffectue une requète GET alors on obtient une Épicerie dans un format JSON avec le id 4 et un code 200 `() {
        val épicerie = créationÉchantillonEpicerie(4, 4, 4, "Super C","superc@gmail.com", "514 839-2987", null)
        Mockito.`when`(service.chercherParCode(4)).thenReturn(épicerie)

        mockMvc.perform(get("/épicerie/4"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.idÉpicerie").value(4))
            .andExpect(jsonPath("$.nom").value("Super C"))
            .andExpect(jsonPath("$.courriel").value("superc@gmail.com"))
    }

    @Test
    fun `Étant donnée une Épicerie avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`() {
        Mockito.`when`(service.chercherParCode(5)).thenThrow(ÉpicerieIntrouvableException::class.java)

        mockMvc.perform(get("/épicerie/5"))
            .andExpect(status().isNotFound)
    }
}
