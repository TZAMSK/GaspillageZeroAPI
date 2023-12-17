package com.GaspillageZeroAPI.Modèle

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("Livraison")
class Livraison (
    @Id
    val code: Int?,
    val commande: Commande?,
    val utilisateur: Utilisateur?,
    val adresse: Adresse?
)