package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.GabaritProduitDAO
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import org.springframework.stereotype.Service

@Service
class GabaritProduitService(val dao: GabaritProduitDAO) {

    fun chercherTous(): List<GabaritProduit> = dao.chercherTous()
    fun chercherParCode(idGabaritProduit: Int): GabaritProduit? = dao.chercherParCode(idGabaritProduit)
    fun ajouter(gabaritProduit: GabaritProduit): GabaritProduit? = dao.ajouter(gabaritProduit)
    fun supprimer(idGabaritProduit: Int): GabaritProduit? = dao.supprimer(idGabaritProduit)
    fun modifier(idGabaritProduit: Int, gabaritProduit: GabaritProduit): GabaritProduit? = dao.modifier(idGabaritProduit, gabaritProduit)
}