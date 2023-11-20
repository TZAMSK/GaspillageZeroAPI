package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.LivraisonDAO
import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Évaluation
import org.springframework.stereotype.Service

@Service
class LivraisonService (private val livraisonDAO: LivraisonDAO){

    fun obtenirLivraisons(): List<Livraison> {
        return livraisonDAO.chercherTous()
    }

    fun obtenirLivraisonParCode(code: Int): Livraison? {
        return livraisonDAO.chercherParCode(code)
    }

    fun ajouterLivraison(livraison: Livraison): Int {
        return livraisonDAO.ajouter(livraison)
    }

    fun modifierLivraison(code: Int, livraison: Livraison): Int {
        return livraisonDAO.modifier(code, livraison)
    }

    fun supprimerLivraison(code: Int) {
        livraisonDAO.supprimer(code)
    }

    /*fun obtenirtoutsÉvaluation():List<Évaluation> {
         return livraisonDAO.obtenirTousÉvaluation()
    }*/
}