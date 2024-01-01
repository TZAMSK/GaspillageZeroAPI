package com.GaspillageZeroAPI.Modèle

data class ItemsPanier(
        val idItemsPanier: Int?,
        val produit: Produit?,
        val quantité: Int) {
}