package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.ProduitDAO
import com.GaspillageZeroAPI.Modèle.Produit
import org.springframework.stereotype.Service

@Service
class ProduitService(val dao: ProduitDAO) {

    fun chercherTous(): List<Produit> = dao.chercherTous()
    fun chercherParCode(idProduit: Int): Produit? = dao.chercherParCode(idProduit)
    fun chercherParÉpicerie(idProduit: Int): List<Produit>? = dao.chercherParÉpicerie(idProduit)
    fun chercherParÉpicerieParCode(code_épicerie: Int, code_produit: Int): Produit? = dao.chercherParÉpicerieParCode(code_épicerie, code_produit)
    fun ajouter(produit: Produit): Produit? = dao.ajouter(produit)
    fun supprimer(idProduit: Int): Produit? = dao.supprimer(idProduit)
    fun modifier(idProduit: Int, produit: Produit): Produit? = dao.modifier(idProduit, produit)
}