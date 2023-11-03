package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Commande
import org.springframework.stereotype.Repository

@Repository
class CommandeDAOImpl: CommandeDAO {

    override fun chercherTous(): List<Commande> = SourceDonnées.commandes
    override fun chercherParCode(idCommande: Int): Commande? = SourceDonnées.commandes.find{it.idCommande == idCommande}

    override fun ajouter(commande: Commande): Commande? {
        SourceDonnées.commandes.add(commande)
        return commande
    }

    override fun supprimer(idCommande: Int): Commande? {
        val commandeSuppimer = SourceDonnées.commandes.find { it.idCommande == idCommande }
        if (commandeSuppimer != null){
            SourceDonnées.commandes.remove(commandeSuppimer)
        }
        return commandeSuppimer
    }

    override fun modifier(idCommande: Int, commande: Commande): Commande? {
        val indexModifierCommande = SourceDonnées.commandes.indexOf(SourceDonnées.commandes.find { it.idCommande == idCommande })
        SourceDonnées.commandes.set(indexModifierCommande, commande)
        return commande
    }

}