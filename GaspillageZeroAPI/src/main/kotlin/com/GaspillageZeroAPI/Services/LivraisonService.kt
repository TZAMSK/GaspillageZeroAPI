package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.LivraisonDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.Livraison
import org.springframework.stereotype.Service


@Service
class LivraisonService (val livraisonDAO: LivraisonDAO){

    fun obtenirLivraisons(): List<Livraison> {
        return livraisonDAO.chercherTous()
    }

    fun obtenirLivraisonParCode(code: Int): Livraison? {
        return livraisonDAO.chercherParCode(code)
    }

    fun obtenirLivraisonParCodeUtilisateurEtCommande(code: Int, commande_code: Int, utilisateur_code: String?, livraison_code: Int): Livraison?{
        return livraisonDAO.chercherParUtilisateurCommandeEtLivraison(code, commande_code, utilisateur_code, livraison_code)
    }

    fun obtenirLivraisonExistanteParCode(code: Int) : Int? {
        return livraisonDAO.chercherLivraisonExistanteParCode(code)
    }

    fun ajouterLivraison(livraison: Livraison): Livraison? = livraisonDAO.ajouter(livraison)

    fun modifierLivraison(code: Int, livraison: Livraison): Livraison? {
        return livraisonDAO.modifier(code, livraison)
    }

    fun supprimerLivraison(code: Int) {
        livraisonDAO.supprimer(code)
    }
}