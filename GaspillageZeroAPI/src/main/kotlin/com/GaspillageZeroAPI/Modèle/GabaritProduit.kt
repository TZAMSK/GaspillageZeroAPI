package com.GaspillageZeroAPI.Modèle

import java.sql.Blob

data class GabaritProduit (
        val idGabaritProduit: Int?,
        val nom: String,
        val description: String,
        val image: Blob?,
        val categorie: String,
        val épicerie: Épicerie?
){
}