package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Services.CommandeService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class CommandeControleurTest {

    @MockBean
    lateinit var service: CommandeService

    @Autowired
    private lateinit var mockMvc: MockMvc

    val commande: Commande = Commande(3,1,2)

    @Test
    fun `Étant donnée la commande avec le code 3, lorsqu'on éffectue une requète GET alors on obtient une commande dans un format JSON avec le id 3 et un code 200 `(){
        TODO()
    }
    @Test
    fun `Étant donnée la commande avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`(){
        TODO()
    }
    @Test
    fun `Étant donnée une commande avec le code 4, lorsqu'on ajoute une commande à l'épicerie avec le code 1 l'aide d'une requète POST on obtient le code 201`(){
        TODO()
    }

    @Test
    fun `Étant donnée une commnde avec le code 3 qui existe déjà, lorsqu'on exécute une requête POST, alors on obtient un code d'erreur 401(conflit)`(){
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
