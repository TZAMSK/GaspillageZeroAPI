package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.CommandeDAO
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.Produit
import org.springframework.stereotype.Service

@Service
class CommandeService(val dao: CommandeDAO) {

    fun chercherTous(): List<Commande> = dao.chercherTous()

    fun chercherParCode(idCommande: Int): Commande? = dao.chercherParCode(idCommande)

    // Utilisateur
    fun chercherCommandesParUtilisateur(idUtilisateur: Int): List<Commande>? = dao.chercherCommandesParUtilisateur(idUtilisateur)
    
    // Épicerie
    fun chercherCommandesParÉpicerie(idÉpicerie: Int): List<Commande>? = dao.chercherCommandesParÉpicerie(idÉpicerie)

    fun ajouter(commande: Commande): Commande? = dao.ajouter(commande)

    fun supprimer(idCommande: Int): Commande? = dao.supprimer(idCommande)

    fun modifier(idCommande: Int, commande: Commande): Commande? = dao.modifier(idCommande, commande)
}