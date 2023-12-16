package com.GaspillageZeroAPI.Modèle

data class GabaritProduitDto (
    val idGabaritProduit: Int?,
    val nom: String,
    val description: String,
    val image: String?, // Image sous forme de chaîne Base64
    val categorie: String,
    val épicerie: Épicerie?
){
}