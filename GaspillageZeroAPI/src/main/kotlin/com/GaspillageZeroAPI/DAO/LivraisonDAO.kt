package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Évaluation

interface LivraisonDAO: DAO<Livraison> {
    override fun chercherTous(): List<Livraison>
    override fun chercherParCode(code: Int): Livraison?
    fun chercherLivraisonExistanteParCode(code: Int): Int?
    override fun ajouter(livraison: Livraison): Livraison?
    override fun modifier(code: Int, livraison: Livraison): Livraison?
    override fun supprimer(code: Int): Livraison?

}