package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.Panier
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Épicerie
import org.springframework.stereotype.Repository

@Repository
class CommandeDAOImpl: CommandeDAO {

    override fun chercherTous(): List<Commande> = SourceDonnées.commandes
    override fun chercherParCode(idCommande: Int): Commande? = SourceDonnées.commandes.find{it.idCommande == idCommande}

    override fun chercherCommandesParUtilisateur(idUtilisateur: Int): List<Commande>{
        val commandesParUtilisateur = SourceDonnées.commandes.filter{ it.idUtilisateur == idUtilisateur }
        return commandesParUtilisateur
    }

    override fun chercherCommandesParÉpicerie(idÉpicerie: Int): List<Commande>{
        val commandesParÉpicerie = SourceDonnées.commandes.filter{ it.idÉpicerie == idÉpicerie }
        return commandesParÉpicerie
    }

    override fun chercherCommandeParUtilisateur(idUtilisateur: Int, idCommande: Int): Commande? = SourceDonnées.commandes.find{it.idUtilisateur == idUtilisateur && it.idCommande == idCommande}

    override fun chercherCommandeParÉpicerie(idÉpicerie: Int, idCommande: Int): Commande? = SourceDonnées.commandes.find{it.idÉpicerie == idÉpicerie && it.idCommande == idCommande}

    override fun chercherCommandeDetailParUtilisateur(idUtilisateur: Int, idCommande: Int): Produit?{
        val idPanier = SourceDonnées.commandes.find { it.idUtilisateur == idUtilisateur && it.idCommande == idCommande }?.idPanier
        val idProduit = SourceDonnées.paniers.find { it.idPanier == idPanier }?.idProduit

        val panierQuantitéProduit = SourceDonnées.paniers.find { it.idPanier == idPanier && it.idProduit == idProduit }?.Quantité
        val panierPrixProduit = SourceDonnées.produits.find { it.idProduit == idProduit }?.prix ?: 0.0

        // val prixTotal = panierQuantitéProduit * panierPrixProduit/
        val prixTotal = panierQuantitéProduit?.times(panierPrixProduit) ?: 0.0

        val produitDetail = SourceDonnées.produits.find { it.idProduit == idProduit }

        return produitDetail?.copy(quantité = panierQuantitéProduit ?: 0, prix = prixTotal)
    }

    override fun chercherCommandeDetailParÉpicerie(idÉpicerie: Int, idCommande: Int): Produit?{
        val idPanier = SourceDonnées.commandes.find { it.idÉpicerie == idÉpicerie && it.idCommande == idCommande }?.idPanier
        val idProduit = SourceDonnées.paniers.find { it.idPanier == idPanier }?.idProduit

        val panierQuantitéProduit = SourceDonnées.paniers.find { it.idPanier == idPanier && it.idProduit == idProduit }?.Quantité
        val panierPrixProduit = SourceDonnées.produits.find { it.idProduit == idProduit }?.prix ?: 0.0

        // val prixTotal = panierQuantitéProduit * panierPrixProduit/
        val prixTotal = panierQuantitéProduit?.times(panierPrixProduit) ?: 0.0

        val produitDetail = SourceDonnées.produits.find { it.idProduit == idProduit }

        return produitDetail?.copy(quantité = panierQuantitéProduit ?: 0, prix = prixTotal)
    }

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