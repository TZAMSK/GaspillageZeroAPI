package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.GabaritProduit
import org.springframework.stereotype.Repository

@Repository
class GabaritProduitDAOImpl: GabaritProduitDAO {

    override fun chercherTous(): List<GabaritProduit> = SourceDonnées.gabariProduits
    override fun chercherParCode(idGabaritProduit: Int): GabaritProduit? = SourceDonnées.gabariProduits.find{it.idGabaritProduit == idGabaritProduit}

    override fun ajouter(gabaritProduit: GabaritProduit): GabaritProduit? {
        SourceDonnées.gabariProduits.add(gabaritProduit)
        return gabaritProduit
    }

    override fun supprimer(idGabaritProduit: Int): GabaritProduit? {
        val gabaritProduitSuppimer = SourceDonnées.gabariProduits.find { it.idGabaritProduit == idGabaritProduit }
        if (gabaritProduitSuppimer != null){
            SourceDonnées.gabariProduits.remove(gabaritProduitSuppimer)
        }
        return gabaritProduitSuppimer
    }

    override fun modifier(idGabaritProduit: Int, gabaritProduit: GabaritProduit): GabaritProduit? {
        val indexModifierGabaritProduit = SourceDonnées.gabariProduits.indexOf(SourceDonnées.gabariProduits.find { it.idGabaritProduit == idGabaritProduit })
        SourceDonnées.gabariProduits.set(indexModifierGabaritProduit, gabaritProduit)
        return gabaritProduit
    }

}