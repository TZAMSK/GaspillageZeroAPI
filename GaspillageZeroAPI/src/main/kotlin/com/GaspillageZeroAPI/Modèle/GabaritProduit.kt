package com.GaspillageZeroAPI.Modèle

import java.sql.Blob

data class GabaritProduit (
        val idGabaritProduit: Int?,
        val nom: String,
        val description: String,
        val image: String?, // Image sous forme de chaîne Base64
        val categorie: String,
        val épicerie: Épicerie?
){
}