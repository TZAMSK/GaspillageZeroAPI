package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Épicerie
import org.springframework.stereotype.Repository

@Repository
class ÉpicerieDAOImpl: ÉpicerieDAO {

    override fun chercherTous(): List<Épicerie> = SourceDonnées.épiceries
    override fun chercherParCode(idÉpicerie: Int): Épicerie? = SourceDonnées.épiceries.find{it.idÉpicerie == idÉpicerie}

    override fun ajouter(épicerie: Épicerie): Épicerie? {
        SourceDonnées.épiceries.add(épicerie)
        return épicerie
    }

    override fun supprimer(idÉpicerie: Int): Épicerie? {
        val épicerieSuppimer = SourceDonnées.épiceries.find { it.idÉpicerie == idÉpicerie }
        if (épicerieSuppimer != null){
            SourceDonnées.épiceries.remove(épicerieSuppimer)
        }
        return épicerieSuppimer
    }

    override fun modifier(idÉpicerie: Int, épicerie: Épicerie): Épicerie? {
        val indexModifierÉpicerie = SourceDonnées.épiceries.indexOf(SourceDonnées.épiceries.find { it.idÉpicerie == idÉpicerie })
        SourceDonnées.épiceries.set(indexModifierÉpicerie, épicerie)
        return épicerie
    }

}