package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.CommandeDAO
import com.GaspillageZeroAPI.DAO.UtilisateurDAO
import com.GaspillageZeroAPI.DAO.ÉpicerieDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.Commande
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class CommandeService(val dao: CommandeDAO, val utilisateurDAO : UtilisateurDAO, val épicerieDAO : ÉpicerieDAO) {

    fun chercherTous(): List<Commande> = dao.chercherTous()

    fun chercherParCode(idCommande: Int): Commande? = dao.chercherParCode(idCommande)


    fun chercherCommandesParUtilisateur(idUtilisateur: Int, code_util: String): List<Commande>?{
        val utilisateurCode = utilisateurDAO.chercherParCode(idUtilisateur)?.code ?: 0
        if (utilisateurDAO.validerUtilisateur(utilisateurCode, code_util)
                && utilisateurDAO.validerDroit(utilisateurDAO.chercherParCode(utilisateurCode), "client")) {
            return dao.chercherCommandesParUtilisateur(idUtilisateur)
        } else {
            throw DroitAccèsInsuffisantException("L'utilisateur " + code_util + " ne peut pas chercher ses commandes.")
        }
    }

    fun chercherCommandesParÉpicerie(idÉpicerie: Int, principal: String): List<Commande>?{
        val utilisateurCode = épicerieDAO.chercherParCode(idÉpicerie)?.utilisateur?.code ?: 0
        if (utilisateurDAO.validerUtilisateur(utilisateurCode, principal)
                && utilisateurDAO.validerDroit(utilisateurDAO.chercherParCode(utilisateurCode), "épicerie")) {
            return dao.chercherCommandesParÉpicerie(idÉpicerie)
        } else {
            throw DroitAccèsInsuffisantException("L'utilisateur " + principal + " ne peut pas chercher ses commandes.")
        }
    }


    fun ajouter(commande: Commande): Commande? = dao.ajouter(commande)

    fun supprimer(idCommande: Int, principal: String) {
        val utilisateur = dao.chercherParCode(idCommande)?.utilisateur
        if(utilisateur?.code != null && utilisateurDAO.validerUtilisateur(utilisateur.code, principal)){
            dao.supprimer(idCommande)
        }else{
            throw DroitAccèsInsuffisantException("L'utilisateur " + principal + " ne peut pas supprimer cette commande")
        }
    }

    fun modifier(idCommande: Int, commande: Commande): Commande? = dao.modifier(idCommande, commande)

    
}