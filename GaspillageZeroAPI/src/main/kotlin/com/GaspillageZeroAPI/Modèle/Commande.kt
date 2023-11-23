package com.GaspillageZeroAPI.Modèle

data class Commande(
        val idCommande: Int?,
        val idÉpicerie: Int?,
        val idUtilisateur: Int?,
        val panier: MutableList<ItemsPanier>
) {
}