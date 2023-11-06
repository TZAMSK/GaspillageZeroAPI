package com.GaspillageZeroAPI.Controleurs

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class ÉpicerieControleurTest {

    @Test
    fun `Étant donnée une Épicerie avec le code 3, lorsqu'on éffectue une requète GET alors on obtient une Épicerie dans un format JSON avec le id 3 et un code 200 `(){
        TODO()
    }
    @Test
    fun `Étant donnée une Épicerie avec le code 4 qui n'existe pas, lorsqu'on éffectue une requète GET alors on obtient un code de retour 404`(){
        TODO()
    }

}
