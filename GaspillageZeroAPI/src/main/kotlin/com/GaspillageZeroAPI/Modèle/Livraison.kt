package com.GaspillageZeroAPI.Modèle

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("Livraison")
class Livraison (
    @Id
    val code: Int?,
    val commande_code: Int?,
    val utilisateur_code: Int?,
    val adresse_id: Int?,
    val nom_gérant: String?
)