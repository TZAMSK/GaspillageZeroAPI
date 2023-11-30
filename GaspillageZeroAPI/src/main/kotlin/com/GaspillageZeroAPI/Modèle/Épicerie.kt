package com.GaspillageZeroAPI.Modèle

import java.sql.Blob

data class Épicerie(
        val idÉpicerie: Int?,
        val Adresse: Adresse?,
        val Utilisateur: Utilisateur?,
        val nom: String,
        val courriel: String,
        val téléphone: String,
        val logo: Blob?
) {
}