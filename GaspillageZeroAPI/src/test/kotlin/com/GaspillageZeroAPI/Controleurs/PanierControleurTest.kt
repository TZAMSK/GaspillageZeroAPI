package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Panier
import com.GaspillageZeroAPI.Services.PanierService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class PanierControleurTest{

    @MockBean
    lateinit var service:PanierService

    @Autowired
    private lateinit var mockMvc: MockMvc

    val panier: Panier = Panier(2,2,2)

    @Test
    fun `Étant donnée le panier avec le idPanier = 2, lorsqu'on éffecture une requête GET avec le id 2 alors on obtient un panier dans le format JSON avec le code de retour 200`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec le idPanier = 3 qui n'existe pas, lorsqu'on éffectue une requête GET avec le ID 3, alors on obtient un code d'erreur 404`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec commandeID = 2, lorsqu'on éffecture une requête GET avec le commandeID = 2, alors on obtient un objet en format JSON et un code de retour 200`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec commandeID = 3 qui n'existe pas, lorsqu'on éffectue une commande avec le commandeID = 3, alors on obtient le code de retour 404`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec le panierID = 3 qui n'existe pas, lorsqu'on ajoute un panier à l'aide d'une requête POST, alors on obtient le code de retour 201`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec le panierID = 2 qui existe déjà, lorsqu'on ajoute un panier à l'aide d'une requête POST, alors on obtient un code d'erreur 409`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec le panierID = 2, lorsqu'on essaie de supprimer avec la requête DELETE alors on obtient le code 200`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec le panierID = 3 qui n'existe pas, lorsqu'on essaie de supprimer avec la requête DELETE alors on obtient le code 404`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec le panierID = 2, lorsqu'on essaie de modifier avec une requête PUT, alors on obtient le code 200`(){
        TODO()
    }

    @Test
    fun `Étant donnée un panier avec le panierID = 3 qui n'existe pas, lorsqu'on essaie de modifier avec une requête PUT alors on obtient le code 404`(){
        TODO()
    }
}