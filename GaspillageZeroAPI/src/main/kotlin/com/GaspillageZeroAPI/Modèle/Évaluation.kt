package com.GaspillageZeroAPI.Modèle
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
 data class Évaluation (
    val idÉvaluation : Int?,
     val nbreÉtoiles : Int?,
     val message : String?,
     val idLivraison : Int?,



 )