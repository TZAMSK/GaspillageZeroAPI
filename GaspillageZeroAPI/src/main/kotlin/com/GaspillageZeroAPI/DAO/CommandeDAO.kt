package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.Produit

interface CommandeDAO : DAO<Commande> {
    override fun chercherTous(): List<Commande>
    override fun chercherParCode(idCommande: Int): Commande?
    override fun ajouter(commande: Commande): Commande?
    override fun supprimer(idCommande: Int): Commande?

    fun chercherCommandesParUtilisateur(idUtilisateur: Int): List<Commande>?
    //fun chercherCommandesParÉpicerie(idÉpicerie: Int): List<Commande>?

}