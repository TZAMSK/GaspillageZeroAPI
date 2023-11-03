package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.ProduitDAO
import com.GaspillageZeroAPI.DAO.ÉpicerieDAO
import com.GaspillageZeroAPI.Modèle.Épicerie
import org.springframework.stereotype.Service

@Service
class ÉpicerieService (val dao: ÉpicerieDAO) {

    fun chercherTous(): List<Épicerie> = dao.chercherTous()
    fun chercherParCode(idÉpicerie: Int): Épicerie? = dao.chercherParCode(idÉpicerie)
    fun ajouter(épicerie: Épicerie): Épicerie? = dao.ajouter(épicerie)
    fun supprimer(idÉpicerie: Int): Épicerie? = dao.supprimer(idÉpicerie)
    fun modifier(idÉpicerie: Int, épicerie: Épicerie): Épicerie? = dao.modifier(idÉpicerie, épicerie)
}