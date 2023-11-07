package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Produit

interface AdresseDAO : DAO<Adresse> {
    override fun chercherTous(): List<Adresse>
    override fun chercherParCode(idAdresse: Int): Adresse?
    override fun ajouter(adresse: Adresse): Adresse?
    override fun supprimer(idCommande: Int): Adresse?
    override fun modifier(idCommande: Int, adresse: Adresse): Adresse?

    fun chercherParUtiliateur(idUtilisateur: Int): Adresse?
}