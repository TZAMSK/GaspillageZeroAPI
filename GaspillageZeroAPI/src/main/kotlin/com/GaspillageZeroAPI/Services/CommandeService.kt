package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.*
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Exceptions.ExceptionAuthentification
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class CommandeService(val dao: CommandeDAO, val utilisateurDAO : UtilisateurDAO,
                      val épicerieDAO : ÉpicerieDAO, val livrasonDAO : LivraisonDAO,
                      val evalDAO : ÉvaluationDAO) {

    fun chercherTous(): List<Commande> = dao.chercherTous()

    fun chercherParCode(idCommande: Int): Commande? {
        val commande = dao.chercherParCode(idCommande)
        val utilisateur = commande?.utilisateur

        return commande
    }


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
        val commande = dao.chercherParCode(idCommande)
        val utilisateur = commande?.utilisateur
        if (utilisateur?.code != null && utilisateurDAO.validerUtilisateur(utilisateur.code, principal)) {
            val livraisons = livrasonDAO.TrouverParCommandeCode(idCommande)
            if (livraisons.isNotEmpty()) {
                for (livraison in livraisons) {
                    if (livraison.code != null) {
                        evalDAO.supprimerParLivraisonCode(livraison.code)
                        livrasonDAO.supprimer(livraison.code)
                    }
                }
            }
            dao.supprimer(idCommande)
        } else {
            throw DroitAccèsInsuffisantException("L'utilisateur $principal ne peut pas supprimer cette commande")
        }
    }

    fun modifier(idCommande: Int, commande: Commande, principal: String){
        val commande = dao.chercherParCode(idCommande)
        val utilisateur = commande?.utilisateur
        if (utilisateur?.code != null && utilisateurDAO.validerUtilisateur(utilisateur.code, principal)) {
            dao.modifier(idCommande, commande)
        }else {
            throw DroitAccèsInsuffisantException("L'utilisateur $principal ne peut pas supprimer cette commande")
        }
    }

    
}