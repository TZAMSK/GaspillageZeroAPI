package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Modèle.Évaluation

interface ÉvaluationDAO {
    fun chercherTous(): List<Évaluation>
    fun modifierÉvaluation(code: Int, avis: Évaluation): Int
    fun chercherParCodeÉvaluation(code: Int): Évaluation?

    fun supprimerParLivraisonCode(livraisonCode: Int)
    fun supprimerParCodeÉvaluation(idÉvaluation: Int): Épicerie?
}