package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Livraison

interface LivraisonDAO {
    fun chercherTous(): List<Livraison>
    fun chercherParCode(code: Int): Livraison?
    fun ajouter(livraison: Livraison): Int
}