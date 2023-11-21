package com.GaspillageZeroAPI.Modèle

data class Utilisateur(
        val idUtilisateur: Int?,
        val nom: String,
        val prénom: String,
        val courriel: String,
        val adresse: Adresse,
        val téléphone: String
)