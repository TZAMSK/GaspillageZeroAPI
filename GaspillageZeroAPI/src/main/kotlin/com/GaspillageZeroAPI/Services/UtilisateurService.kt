package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.UtilisateurDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Exceptions.ExceptionAuthentification
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Modèle.Utilisateur
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UtilisateurService(val dao: UtilisateurDAO) {

    fun chercherTous(): List<Utilisateur> = dao.chercherTous()
    fun chercherParCode(idUtilisateur: Int): Utilisateur? = dao.chercherParCode(idUtilisateur)
    fun validerCodeAuth0(code: Int): String? = dao.validerCodeAuth0(code)
    fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        val idUtilisateur = utilisateur.code

        if (idUtilisateur != null && dao.chercherParCode(idUtilisateur) != null) {
            throw ExceptionConflitRessourceExistante("Utilisateur existe déjà")
        }

        return dao.ajouter(utilisateur)
    }
    fun supprimer(idUtilisateur: Int): Utilisateur? = dao.supprimer(idUtilisateur)

    fun modifier(idUtilisateur: Int, code_util: String, utilisateur: Utilisateur): Utilisateur?{

        if (dao.validerUtilisateur(idUtilisateur, code_util)) {
            dao.modifier(idUtilisateur, utilisateur)
        } else {
            throw DroitAccèsInsuffisantException("Seul l'utilisateur' " + code_util + " peut modifier ses informations.")
        }
        return utilisateur
    }

    fun validerUtilisateur(idUtilisateur: Int, code_courant: String): Boolean = dao.validerUtilisateur(idUtilisateur, code_courant)

}