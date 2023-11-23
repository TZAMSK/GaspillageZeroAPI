package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Produit

interface ProduitDAO : DAO<Produit> {
    override fun chercherTous(): List<Produit>
    override fun chercherParCode(idProduit: Int): Produit?
    override fun ajouter(produit: Produit):Produit?
    override fun supprimer(idProduit: Int): Produit?
    override fun modifier(idProduit: Int, produit: Produit): Produit?

    fun chercherParÉpicerie(idProduit: Int): List<Produit>?
    fun chercherParÉpicerieParCode(code_épicerie: Int, code_produit: Int): Produit?
}