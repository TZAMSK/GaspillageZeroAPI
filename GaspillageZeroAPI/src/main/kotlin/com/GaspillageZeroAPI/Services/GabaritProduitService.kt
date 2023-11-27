package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.GabaritProduitDAO
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import org.springframework.stereotype.Service

@Service
class GabaritProduitService(val dao: GabaritProduitDAO) {

    fun chercherTous(): List<GabaritProduit> = dao.chercherTous()
    fun chercherParCode(idGabaritProduit: Int): GabaritProduit? = dao.chercherParCode(idGabaritProduit)
    fun ajouter(gabaritProduit: GabaritProduit): GabaritProduit? = dao.ajouter(gabaritProduit)

    fun supprimer(idGabaritProduit: Int): Boolean {
        val gabarit = dao.chercherParCode(idGabaritProduit)
        if (gabarit != null) {
            dao.supprimer(idGabaritProduit)
            return true
        }
        return false
    }

    fun modifier(idGabaritProduit: Int, gabaritProduit: GabaritProduit): Boolean {
        val gabaritExistant = dao.chercherParCode(idGabaritProduit)
        if (gabaritExistant != null) {
            dao.modifier(idGabaritProduit, gabaritProduit)
            return true
        }
        return false
    }
}
