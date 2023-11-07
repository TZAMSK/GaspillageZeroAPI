package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.AdresseDAO
import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Produit
import org.springframework.stereotype.Service

@Service
class AdresseService(val dao: AdresseDAO) {

    fun chercherTous(): List<Adresse> = dao.chercherTous()
    fun chercherParCode(idAdresse: Int): Adresse? = dao.chercherParCode(idAdresse)
    fun ajouter(adresse: Adresse): Adresse? = dao.ajouter(adresse)
    fun supprimer(idAdresse: Int): Adresse? = dao.supprimer(idAdresse)
    fun modifier(idAdresse: Int, adresse: Adresse): Adresse? = dao.modifier(idAdresse, adresse)

    fun chercherParUtiliateur(idUtilisateur: Int): Adresse? = dao.chercherParUtiliateur(idUtilisateur)
}