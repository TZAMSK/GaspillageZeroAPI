package com.GaspillageZeroAPI.Modèle

import java.util.Date

data class Produit(
        val idProduit: Int? ,
        val nom: String,
        val date_expiration: Date,
        var quantité: Int,
        val prix: Double,
        val Épicerie: Épicerie?,
        val GabaritProduit: GabaritProduit?) {
}