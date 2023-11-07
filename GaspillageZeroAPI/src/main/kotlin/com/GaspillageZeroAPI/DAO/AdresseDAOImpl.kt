package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Adresse
import com.GaspillageZeroAPI.Modèle.Produit
import org.springframework.stereotype.Repository

@Repository
class AdresseDAOImpl: AdresseDAO {

    override fun chercherTous(): List<Adresse> = SourceDonnées.adresses
    override fun chercherParCode(idAdresse: Int): Adresse? = SourceDonnées.adresses.find{it.idAdresse == idAdresse}

    override fun chercherParUtiliateur(idUtilisateur: Int): Adresse? = SourceDonnées.utilisateurs.find{ it.idUtilisateur == idUtilisateur}?.adresse
    override fun ajouter(adresse: Adresse): Adresse? {
        SourceDonnées.adresses.add(adresse)
        return adresse
    }

    override fun supprimer(idAdresse: Int): Adresse? {
        val adresseSuppimer = SourceDonnées.adresses.find { it.idAdresse == idAdresse }
        if (adresseSuppimer != null){
            SourceDonnées.adresses.remove(adresseSuppimer)
        }
        return adresseSuppimer
    }

    override fun modifier(idAdresse: Int, adresse: Adresse): Adresse? {
        val indexModifierAdresse = SourceDonnées.adresses.indexOf(SourceDonnées.adresses.find { it.idAdresse == idAdresse })
        SourceDonnées.adresses.set(indexModifierAdresse, adresse)
        return adresse
    }

}