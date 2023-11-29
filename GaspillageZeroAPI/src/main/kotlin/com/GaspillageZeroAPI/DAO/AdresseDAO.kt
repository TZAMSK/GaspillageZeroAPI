package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Produit

interface AdresseDAO : DAO<Adresse> {

    override fun chercherParCode(idAdresse: Int): Adresse?

}