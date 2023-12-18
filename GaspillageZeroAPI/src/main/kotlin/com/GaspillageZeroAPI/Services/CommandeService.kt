package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.CommandeDAO
import com.GaspillageZeroAPI.DAO.UtilisateurDAO
import com.GaspillageZeroAPI.DAO.ÉpicerieDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Exceptions.ExceptionAuthentification
import com.GaspillageZeroAPI.Modèle.Commande
import org.springframework.stereotype.Service
import java.security.Principal

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


    fun ajouter(commande: Commande, principal: Principal?): Commande? {
        var uneCommande: Commande? = null
        if(principal == null){
            throw ExceptionAuthentification("Vous devez vous enthentifier afin de pouvoir faire cet action")
        }

        if(utilisateurDAO.validerUtilisateur(commande.utilisateur?.code ?: 0, principal.name)){
            uneCommande = dao.ajouter(commande)
        }else{
            throw DroitAccèsInsuffisantException("Vous ne pouvez pas creer de commande pour les autres compte sauf le votre ")
        }
        return uneCommande
    }

    fun supprimer(idCommande: Int, principal: String) {
        val utilisateur = dao.chercherParCode(idCommande)?.utilisateur
        if(utilisateur?.code != null && utilisateurDAO.validerUtilisateur(utilisateur.code, principal)){
            dao.supprimer(idCommande)
        }else{
            throw DroitAccèsInsuffisantException("Seul l'utilisateur " + principal + "peut supprimer cette commande")
        }
    }

    fun modifier(idCommande: Int, commande: Commande): Commande? = dao.modifier(idCommande, commande)

    
}