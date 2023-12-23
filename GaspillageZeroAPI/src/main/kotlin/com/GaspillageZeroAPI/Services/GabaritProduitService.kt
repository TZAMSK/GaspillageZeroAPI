package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.GabaritProduitDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Modèle.Produit
import org.springframework.stereotype.Service

@Service
class GabaritProduitService(val dao: GabaritProduitDAO) {

    fun chercherTous(): List<GabaritProduit> = dao.chercherTous()
    fun chercherParCode(idGabaritProduit: Int): GabaritProduit? = dao.chercherParCode(idGabaritProduit)
    fun chercherParÉpicerie(idGabarit: Int): List<GabaritProduit>? = dao.chercherParÉpicerie(idGabarit)
    fun ajouter(gabaritProduit: GabaritProduit, code_util: String): GabaritProduit?{
        if (dao.estGerantParCode(code_util)) {
            return dao.ajouter(gabaritProduit)
        } else {
            throw DroitAccèsInsuffisantException("Seuls les utilisateurs avec le role gérant peuvent ajouter un Gabarit Produit. L'utilisateur " + code_util + " n'est pas inscrit comme gérant de l'épicerie.")
        }
    }
    fun supprimer(idGabaritProduit: Int, code_util: String): Boolean {
        val gabarit = dao.chercherParCode(idGabaritProduit)
        if (gabarit != null ) {
            if (dao.estGerantParCode(code_util)) {
                dao.supprimer(idGabaritProduit)
                return true
            }else {
                throw DroitAccèsInsuffisantException("Seuls les utilisateurs avec le role gérant peuvent ajouter un Gabarit Produit. L'utilisateur " + code_util + " n'est pas inscrit comme gérant de l'épicerie.")
            }
        }
        return false
    }

    fun modifier(idGabaritProduit: Int, gabaritProduit: GabaritProduit, code_util: String): Boolean {
        val gabarit = dao.chercherParCode(idGabaritProduit)
        if (gabarit != null) {
            if (dao.estGerantParCode(code_util)) {
                dao.modifier(idGabaritProduit, gabaritProduit)
                return true
            }else {
                throw DroitAccèsInsuffisantException("Seuls les utilisateurs avec le role gérant peuvent ajouter un Gabarit Produit. L'utilisateur " + code_util + " n'est pas inscrit comme gérant de l'épicerie.")
            }
        }
        return false
    }
}
