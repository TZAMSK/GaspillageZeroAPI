package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Épicerie
import org.springframework.stereotype.Repository

@Repository
class ProduitDAOImpl : ProduitDAO {

    override fun chercherTous(): List<Produit> = SourceDonnées.produits
    override fun chercherParCode(idProduit: Int): Produit? = SourceDonnées.produits.find{it.idProduit == idProduit}
    override fun chercherParÉpicerie(idÉpicerie: Int): List<Produit> = SourceDonnées.épiceries.find{it.idÉpicerie == idÉpicerie}?.produits ?: emptyList()
    override fun chercherParÉpicerieParCode(code_épicerie: Int, code_produit: Int): Produit? = SourceDonnées.épiceries.find{it.idÉpicerie == code_épicerie}?.produits?.find{it.idProduit == code_produit} ?: null

    override fun ajouter(produit: Produit): Produit? {
        SourceDonnées.produits.add(produit)
        return produit
    }
    override fun supprimer(idProduit: Int): Produit? {
        val produitSuppimer = SourceDonnées.produits.find { it.idProduit == idProduit }
        if (produitSuppimer != null) {
            SourceDonnées.produits.remove(produitSuppimer)
        }
        return produitSuppimer
    }
    /*
    override fun supprimer(idProduit: Int): Produit? = SourceDonnées.produits.removeAt(SourceDonnées.produits.indexOf(SourceDonnées.produits.find { it.idProduit == idProduit }))
     */

    override fun modifier(idProduit: Int, produit: Produit): Produit? {
        val indexModifierProduit = SourceDonnées.produits.indexOf(SourceDonnées.produits.find { it.idProduit == idProduit })
        SourceDonnées.produits.set(indexModifierProduit, produit)
        return produit
    }
    /*
    override fun modifierNom(idProduit: Int, produit: Produit): Produit? = SourceDonnées.produits.set(SourceDonnées.produits.indexOf(SourceDonnées.produits.find { it.idProduit == idProduit }), produit)
     */
}