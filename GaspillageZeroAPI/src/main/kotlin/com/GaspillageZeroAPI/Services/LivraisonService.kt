package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.LivraisonDAO
import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
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