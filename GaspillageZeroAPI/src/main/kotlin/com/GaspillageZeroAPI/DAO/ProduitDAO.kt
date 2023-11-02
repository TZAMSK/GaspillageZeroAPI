package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Produit

interface ProduitDAO : DAO<Produit> {
    override fun chercherTous(): List<Produit>
    override fun chercherParCode(id: Int): Produit?
    override fun ajouter(produit: Produit):Produit?
    override fun supprimer(id: Int): Produit?
    override fun modifier(id: Int, produit: Produit): Produit?

    fun chercherParÉpicerie(id: Int): List<Produit>
    fun chercherParÉpicerieParCode(code_épicerie: Int, code_produit: Int): Produit?
}