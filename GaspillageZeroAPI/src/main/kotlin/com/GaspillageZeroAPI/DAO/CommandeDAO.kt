package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Commande

interface CommandeDAO : DAO<Commande> {
    override fun chercherTous(): List<Commande>
    override fun chercherParCode(idCommande: Int): Commande?
    override fun ajouter(commande: Commande): Commande?
    override fun supprimer(idCommande: Int): Commande?
    override fun modifier(idCommande: Int, commande: Commande): Commande?
}