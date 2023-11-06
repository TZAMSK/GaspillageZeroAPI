package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.Panier
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Utilisateur

interface CommandeDAO : DAO<Commande> {
    override fun chercherTous(): List<Commande>
    override fun chercherParCode(idCommande: Int): Commande?
    override fun ajouter(commande: Commande): Commande?
    override fun supprimer(idCommande: Int): Commande?
    override fun modifier(idCommande: Int, commande: Commande): Commande?

    fun chercherCommandesParUtilisateur(idUtilisateur: Int): List<Commande>
    fun chercherCommandesParÉpicerie(idÉpicerie: Int): List<Commande>
    fun chercherCommandeParUtilisateur(code_utilisateur: Int, code_commande: Int): Commande?
    fun chercherCommandeDetailParUtilisateur(code_utilisateur: Int, code_commande: Int): Produit?
    fun chercherCommandeParÉpicerie(code_épicerie: Int, code_commande: Int): Commande?

    fun chercherCommandeDetailParÉpicerie(code_épicerie: Int, code_commande: Int): Produit?
}