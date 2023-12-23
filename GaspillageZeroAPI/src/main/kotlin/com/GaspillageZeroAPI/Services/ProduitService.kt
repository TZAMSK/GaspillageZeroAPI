package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.ProduitDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Modèle.Produit
import org.springframework.stereotype.Service

@Service
class ProduitService(val dao: ProduitDAO) {

    fun chercherTous(): List<Produit> = dao.chercherTous()
    fun chercherParCode(idProduit: Int): Produit? = dao.chercherParCode(idProduit)
    fun chercherParÉpicerie(idProduit: Int): List<Produit>? = dao.chercherParÉpicerie(idProduit)
    fun chercherParÉpicerieParCode(code_épicerie: Int, code_produit: Int): Produit? = dao.chercherParÉpicerieParCode(code_épicerie, code_produit)

    fun ajouter(produit: Produit, code_util: String): Produit?{
        if (dao.estGerantParCode(code_util)) {
            return dao.ajouter(produit)
        } else {
            throw DroitAccèsInsuffisantException("Seuls les utilisateurs avec le role gérant peuvent ajouter un Produit. L'utilisateur " + code_util + " n'est pas inscrit comme gérant de l'épicerie.")
        }
    }

    fun supprimer(idProduit: Int, code_util: String): Boolean {
        val produit = dao.chercherParCode(idProduit)
        if (produit != null ) {
            if (dao.estGerantParCode(code_util)) {
                dao.supprimer(idProduit)
                return true
            }else {
                throw DroitAccèsInsuffisantException("Seuls les utilisateurs avec le role gérant peuvent supprimer un Produit. L'utilisateur " + code_util + " n'est pas inscrit comme gérant de l'épicerie.")
            }
        }
        return false
    }

    fun modifier(idProduit: Int, produitmodifié: Produit, code_util: String): Boolean {
        val produit = dao.chercherParCode(idProduit)
        if (produit != null) {
            if (dao.estGerantParCode(code_util)) {
                dao.modifier(idProduit, produitmodifié)
                return true
            }else {
                throw DroitAccèsInsuffisantException("Seuls les utilisateurs avec le role gérant peuvent modifier un Produit. L'utilisateur " + code_util + " n'est pas inscrit comme gérant de l'épicerie.")
            }
        }
        return false
    }
}
