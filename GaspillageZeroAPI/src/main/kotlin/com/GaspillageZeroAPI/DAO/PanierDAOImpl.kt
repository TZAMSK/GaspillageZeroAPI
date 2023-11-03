package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Panier
import org.springframework.stereotype.Repository

@Repository
class PanierDAOImpl: PanierDAO {

    override fun chercherTous(): List<Panier> = SourceDonnées.paniers
    override fun chercherParCode(idPanier: Int): Panier? = SourceDonnées.paniers.find{it.idPanier == idPanier}

    override fun ajouter(panier: Panier): Panier? {
        SourceDonnées.paniers.add(panier)
        return panier
    }

    override fun supprimer(idPanier: Int): Panier? {
        val panierSuppimer = SourceDonnées.paniers.find { it.idPanier == idPanier }
        if (panierSuppimer != null){
            SourceDonnées.paniers.remove(panierSuppimer)
        }
        return panierSuppimer
    }

    override fun modifier(idPanier: Int, panier: Panier): Panier? {
        val indexModifierPanier = SourceDonnées.paniers.indexOf(SourceDonnées.paniers.find { it.idPanier == idPanier })
        SourceDonnées.paniers.set(indexModifierPanier, panier)
        return panier
    }

}