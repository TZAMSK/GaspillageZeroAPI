package com.GaspillageZeroAPI.Modèle

data class Commande(
        val idCommande: Int?,
        val épicerie: Épicerie?,
        val utilisateur: Utilisateur?,
        val panier: MutableList<ItemsPanier>
) { }