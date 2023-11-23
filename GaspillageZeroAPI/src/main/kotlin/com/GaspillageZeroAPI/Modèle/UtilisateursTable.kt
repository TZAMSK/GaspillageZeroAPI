package com.GaspillageZeroAPI.Modèle

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("Utilisateur")
data class UtilisateursTable(
    @Id
    val code: Int?,
    val nom: String,
    val prénom: String,
    val courriel: String,
    val adresse_id: Int,
    val téléphone: String
)
