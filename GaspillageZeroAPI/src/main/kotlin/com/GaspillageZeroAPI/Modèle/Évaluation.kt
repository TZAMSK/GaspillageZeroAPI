package com.GaspillageZeroAPI.Modèle
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("avis")
 data class Évaluation (
    val idÉvaluation : Int?,
    val idLivraison : Int?,
    val nbreÉtoiles : Int?,
    val message : String?,




 )