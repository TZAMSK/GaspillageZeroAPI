package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.ItemsPanier

interface ItemsPanierDAO : DAO<ItemsPanier>{
    override fun chercherTous(): List<ItemsPanier>

    override fun chercherParCode(idItemsPanier: Int): ItemsPanier?

    override fun supprimer(idItemsPanier: Int): ItemsPanier?

    fun estGerantParCode(code: String): Boolean

}