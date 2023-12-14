package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.LivraisonDAO
import com.GaspillageZeroAPI.DAO.LivraisonDAOImpl
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.Livraison
import org.springframework.stereotype.Service

@Service
class LivraisonService (val livraisonDAO: LivraisonDAO, val livraisonDAOImpl: LivraisonDAOImpl){

    fun obtenirLivraisons(): List<Livraison> {
        return livraisonDAO.chercherTous()
    }

    fun obtenirLivraisonParCode(code: Int): Livraison? {
        return livraisonDAO.chercherParCode(code)
    }

    fun obtenirGérants(code:Int, nom_gérant: String): Boolean{
        return livraisonDAO.validerGérants(code, nom_gérant)
    }

    fun obtenirLivraisonExistanteParCode(code: Int) : Int? {
        return livraisonDAO.chercherLivraisonExistanteParCode(code)
    }

    fun ajouterLivraison(livraison: Livraison): Livraison? = livraisonDAO.ajouter(livraison)

    fun modifierLivraison(code: Int, livraison: Livraison): Livraison? {
        return livraisonDAO.modifier(code, livraison)
    }

    fun supprimerLivraison(code: Int, nom_gérant: String) {
        if (livraisonDAO.validerGérants(code, nom_gérant)) {
            livraisonDAO.supprimer(code)
        } else {
            throw DroitAccèsInsuffisantException("Seuls les gérants de l'épicerie " + code + " peuvent le désinscrire. L'utilisateur " + nom_gérant + " n'est pas inscrit comme gérant de cette épicerie.")
        }
    }
}