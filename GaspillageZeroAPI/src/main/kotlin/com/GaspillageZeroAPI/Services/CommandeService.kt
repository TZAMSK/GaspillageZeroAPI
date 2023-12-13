package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.CommandeDAO
import com.GaspillageZeroAPI.DAO.UtilisateurDAO
import com.GaspillageZeroAPI.DAO.ÉpicerieDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.Commande
import org.springframework.stereotype.Service

@Service
class CommandeService(val dao: CommandeDAO, val utilisateurDAO : UtilisateurDAO, val épicerieDAO : ÉpicerieDAO) {

    fun chercherTous(): List<Commande> = dao.chercherTous()

    fun chercherParCode(idCommande: Int): Commande? = dao.chercherParCode(idCommande)

    // Utilisateur
    //fun chercherCommandesParUtilisateur(idUtilisateur: Int): List<Commande>? = dao.chercherCommandesParUtilisateur(idUtilisateur)
    fun chercherCommandesParUtilisateur(idUtilisateur: Int, code_util: String): List<Commande>?{
        if (utilisateurDAO.validerUtilisateur(idUtilisateur, code_util)) {
            return dao.chercherCommandesParUtilisateur(idUtilisateur)
        } else {
            throw DroitAccèsInsuffisantException("Seul l'utilisateur " + code_util + " peut chercher ses commandes.")
        }
    }
    
    // Épicerie
    //fun chercherCommandesParÉpicerie(idÉpicerie: Int): List<Commande>? = dao.chercherCommandesParÉpicerie(idÉpicerie)


    fun chercherCommandesParÉpicerie(idÉpicerie: Int, principal: String): List<Commande>?{
        if (utilisateurDAO.validerUtilisateur(épicerieDAO.chercherParCode(idÉpicerie)?.utilisateur?.code ?: 0, principal)) {
            return dao.chercherCommandesParÉpicerie(idÉpicerie)
        } else {
            throw DroitAccèsInsuffisantException("Seul l'utilisateur " + principal + " peut chercher ses commandes.")
        }
    }


    fun ajouter(commande: Commande): Commande? = dao.ajouter(commande)

    fun supprimer(idCommande: Int): Commande? = dao.supprimer(idCommande)

    fun modifier(idCommande: Int, commande: Commande): Commande? = dao.modifier(idCommande, commande)

    
}