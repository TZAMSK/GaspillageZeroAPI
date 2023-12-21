package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.ItemsPanierDAO
import com.GaspillageZeroAPI.Exceptions.DroitAccèsInsuffisantException
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Modèle.ItemsPanier
import org.springframework.stereotype.Service

@Service

class ItemsPanierService(val dao:ItemsPanierDAO) {

    fun chercherTous(): List<ItemsPanier> = dao.chercherTous()

    fun chercherParCode(idItemsPanier: Int): ItemsPanier? = dao.chercherParCode(idItemsPanier)

    fun supprimer(idItemsPanier: Int, code_util: String): Boolean  {
        val itemsPanier = dao.chercherParCode(idItemsPanier)
        if (itemsPanier != null ) {
            if (dao.estGerantParCode(code_util)) {
                dao.supprimer(idItemsPanier)
                return true
            }else {
                throw DroitAccèsInsuffisantException("Seuls les utilisateurs avec le role gérant peuvent supprimmer un items paniers. L'utilisateur " + code_util + " n'est pas inscrit comme gérant de l'épicerie.")
            }
        }
        return false
    }
}