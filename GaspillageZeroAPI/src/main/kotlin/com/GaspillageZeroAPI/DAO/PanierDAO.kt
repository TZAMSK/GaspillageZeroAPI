package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Panier

interface PanierDAO : DAO<Panier> {
    override fun chercherTous(): List<Panier>
    override fun chercherParCode(idPanier: Int): Panier?

    override fun ajouter(panier: Panier): Panier?
    override fun supprimer(idPanier: Int): Panier?
    override fun modifier(idPanier: Int, panier: Panier): Panier?

    fun chercherContenueParCommande(code_commande: Int): Panier?
}