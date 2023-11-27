package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.ÉpicerieDAO
import com.GaspillageZeroAPI.Modèle.Épicerie
import org.springframework.stereotype.Service

@Service
class ÉpicerieService(val dao: ÉpicerieDAO) {

    fun chercherTous(): List<Épicerie> = dao.chercherTous()
    fun chercherParCode(idÉpicerie: Int): Épicerie? = dao.chercherParCode(idÉpicerie)
    fun ajouter(épicerie: Épicerie): Boolean { return dao.ajouter(épicerie) != null }
    fun supprimer(idÉpicerie: Int): Boolean { return dao.supprimer(idÉpicerie) != null }
    fun modifier(idÉpicerie: Int, épicerie: Épicerie): Boolean { return dao.modifier(idÉpicerie, épicerie) != null }
}
