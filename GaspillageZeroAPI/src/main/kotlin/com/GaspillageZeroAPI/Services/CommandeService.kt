package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.CommandeDAO
import com.GaspillageZeroAPI.Modèle.Commande
import org.springframework.stereotype.Service

@Service
class CommandeService(val dao: CommandeDAO) {

    fun chercherTous(): List<Commande> = dao.chercherTous()
    fun chercherParCode(idCommande: Int): Commande? = dao.chercherParCode(idCommande)
    fun ajouter(commande: Commande): Commande? = dao.ajouter(commande)
    fun supprimer(idCommande: Int): Commande? = dao.supprimer(idCommande)
    fun modifier(idCommande: Int, commande: Commande): Commande? = dao.modifier(idCommande, commande)
}