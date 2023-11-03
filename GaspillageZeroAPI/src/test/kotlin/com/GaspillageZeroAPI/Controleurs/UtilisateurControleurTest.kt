package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Services.UtilisateurService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class UtilisateurControleurTest{

    @MockBean
    lateinit var service: UtilisateurService

    @Autowired
    private lateinit var mockMvc: MockMvc

    val utilisateur: Utilisateur = Utilisateur(2, "Africa", "Toto", "toto@afri.ca", Adresse(3,"123", "Saint-chose", "Québec", "B1B 1B1", "Canada"),"(123)456-7890")

    @Test
    fun `Étant donnée un utilisateur avec le id 2, lorsqu'on éffectue un requète GET alors on obtien un JSON qui contient un objet Utilisateur avec le ID 2 et le code 200`(){
        TODO()
    }

    @Test
    fun `Étant donnée un Utilisateur avec le id 3 qui n'existe pas, lorsqu'on éffectue un requête GET alors on obtient un code de retour 404`(){
        TODO()
    }

    @Test
    fun `Étant donnée un Utilisateur avec le ID 3 qui n'existe, lorsqu'on éffectue un requête POST alors on obtient un code 201`(){
        TODO()
    }

    @Test
    fun `Étant donnée un Utilsiateur avec le ID 2, lorsqu'on exécute un requête DELETE, on obtient le code 200`(){
        TODO()
    }


    @Test
    fun `Étant donnée un Utilsiateur avec le ID 3 qui n'existe pas, lorsqu'on exécute un requête DELETE, on obtient le code 404`(){
        TODO()
    }
}
