package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.UtilisateurDAO
import com.GaspillageZeroAPI.Modèle.Utilisateur
import org.springframework.stereotype.Service

@Service
class UtilisateurService(val dao: UtilisateurDAO) {

    fun chercherTous(): List<Utilisateur> = dao.chercherTous()
    fun chercherParCode(idUtilisateur: Int): Utilisateur? = dao.chercherParCode(idUtilisateur)
    fun ajouter(utilisateur: Utilisateur): Utilisateur? = dao.ajouter(utilisateur)
    fun supprimer(idUtilisateur: Int): Utilisateur? = dao.supprimer(idUtilisateur)
    fun modifier(idUtilisateur: Int, utilisateur: Utilisateur): Utilisateur? = dao.modifier(idUtilisateur, utilisateur)
}