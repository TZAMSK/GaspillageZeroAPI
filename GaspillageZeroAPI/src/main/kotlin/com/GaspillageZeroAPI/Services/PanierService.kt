package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.PanierDAO
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.Panier
import org.springframework.stereotype.Service

@Service
class PanierService(val dao: PanierDAO) {

    fun chercherTous(): List<Panier> = dao.chercherTous()
    fun chercherParCode(idPanier: Int): Panier? = dao.chercherParCode(idPanier)
    fun chercherContenuParCommande(idCommande: Int): Panier? = dao.chercherContenueParCommande(idCommande)
    fun ajouter(panier: Panier): Panier? = dao.ajouter(panier)
    fun supprimer(idPanier: Int): Panier? = dao.supprimer(idPanier)
    fun modifier(idPanier: Int, panier: Panier): Panier? = dao.modifier(idPanier, panier)
}