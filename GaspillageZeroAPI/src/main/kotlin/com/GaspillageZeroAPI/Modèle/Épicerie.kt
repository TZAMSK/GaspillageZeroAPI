package com.GaspillageZeroAPI.Modèle

data class Épicerie(
        val idÉpicerie: Int?,
        val nom: String,
        val courriel: String,
        val téléphone: String,
        val produits: List<Produit> = mutableListOf(),
        val gabaritProduits: List<GabaritProduit> = mutableListOf()) {
}