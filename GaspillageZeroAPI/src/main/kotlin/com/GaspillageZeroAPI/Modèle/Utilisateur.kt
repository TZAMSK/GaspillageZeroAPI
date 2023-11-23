package com.GaspillageZeroAPI.Modèle

import java.util.*

data class Utilisateur(
        val idUtilisateur: Int?,
        val nom: String,
        val prénom: String,
        val courriel: String,
        val adresse: Adresse,
        val téléphone: String,
        val rôle: MutableList<Utilisateur_Rôle>) {
}