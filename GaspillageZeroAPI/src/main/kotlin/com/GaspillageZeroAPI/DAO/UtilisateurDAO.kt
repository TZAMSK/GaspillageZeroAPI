package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Modèle.UtilisateursTable

interface UtilisateurDAO : DAO<Utilisateur> {
    override fun chercherTous(): List<Utilisateur>
    override fun chercherParCode(id: Int): Utilisateur?
    override fun ajouter(utilisateur: Utilisateur): Utilisateur?
    override fun supprimer(id: Int): Utilisateur?
    override fun modifier(id: Int, utilisateur: Utilisateur): Utilisateur?
    fun validerUtilisateur(code_utilisateur: Int, code_courant: String?): Boolean
    fun validerDroit(utilisateur: Utilisateur?, role: String?): Boolean
}