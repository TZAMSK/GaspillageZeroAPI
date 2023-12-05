package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.UtilisateurDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Modèle.UtilisateursTable
import org.springframework.stereotype.Service

@Service
class UtilisateurService(val dao: UtilisateurDAO) {

    fun chercherTous(): List<Utilisateur> = dao.chercherTous()
    fun chercherParCode(idUtilisateur: Int): Utilisateur? = dao.chercherParCode(idUtilisateur)
    fun ajouter(utilisateur: Utilisateur): Utilisateur? = dao.ajouter(utilisateur)
    fun supprimer(idUtilisateur: Int): Utilisateur? = dao.supprimer(idUtilisateur)
    //fun modifier(idUtilisateur: Int, utilisateur: Utilisateur): Utilisateur? = dao.modifier(idUtilisateur, utilisateur)

    fun modifier(idUtilisateur: Int, code_util: String, utilisateur: Utilisateur): Utilisateur?{
        if (dao.validerUtilisateur(idUtilisateur, code_util)) {
            dao.modifier(idUtilisateur, utilisateur)
        } else {
            throw DroitAccèsInsuffisantException("Seul l'utilisateur' " + code_util + " peut modifier ses informations.")
        }
        return utilisateur
    }

}