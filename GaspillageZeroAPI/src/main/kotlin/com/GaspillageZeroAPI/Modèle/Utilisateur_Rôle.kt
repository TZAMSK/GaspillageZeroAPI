package com.GaspillageZeroAPI.Modèle

import java.util.*

class Utilisateur_Rôle(
        val idUtilisateur: Int,
        val rôle: String,
        val horodatage: Date = Date()
) {
}