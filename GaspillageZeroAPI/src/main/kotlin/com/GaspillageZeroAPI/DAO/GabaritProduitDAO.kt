package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Modèle.Produit

interface GabaritProduitDAO : DAO<GabaritProduit> {
    override fun chercherTous(): List<GabaritProduit>
    override fun chercherParCode(id: Int): GabaritProduit?
    override fun ajouter(gabaritProduit:GabaritProduit): GabaritProduit?
    override fun supprimer(id: Int): GabaritProduit?
    override fun modifier(id: Int, gabaritProduit: GabaritProduit): GabaritProduit?
    fun estGerantParCode(code: String): Boolean
}