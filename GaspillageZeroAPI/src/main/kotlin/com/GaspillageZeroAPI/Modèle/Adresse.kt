package com.GaspillageZeroAPI.Modèle

data class Adresse(
        val idAdresse: Int?,
        val numéro_municipal: String,
        val rue: String,
        val ville: String,
        val province: String,
        val code_postal: String,
        val pays: String ) {
}