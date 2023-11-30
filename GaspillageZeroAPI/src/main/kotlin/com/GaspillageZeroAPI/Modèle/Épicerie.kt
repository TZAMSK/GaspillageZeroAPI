package com.GaspillageZeroAPI.Modèle

import java.sql.Blob

data class Épicerie(
        val idÉpicerie: Int?,
        val adresse: Adresse?,
        val utilisateur: Utilisateur?,
        val nom: String,
        val courriel: String,
        val téléphone: String,
        val logo: Blob?
) {
}