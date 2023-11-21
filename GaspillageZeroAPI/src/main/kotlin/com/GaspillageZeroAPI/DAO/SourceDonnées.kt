package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.*
import org.springframework.stereotype.Component
import java.util.Date

@Component
class SourceDonnées {

    companion object {
        val produits = mutableListOf(
                Produit(1, "Tomates", Date(2024,3,5), 230, 4.99),
                Produit(2, "Patate", Date(2024,5,16), 24, 12.00),
                Produit(3, "Vinaigre", Date(2024,9,23), 12, 7.00),
                Produit(4, "Miel", Date(2024, 2,12), 24, 6.00),
                Produit(5, "Haricots", Date(2024, 5, 19), 31, 6.50),
                Produit(6, "Riz", Date(2023, 12, 31), 13, 8.50),
        )
        val gabariProduits = mutableListOf(
                GabaritProduit(1, "Bla bla", "Bla bla bla", null, "Légumes", produits),
                GabaritProduit(2, "Bla bla", "Bla bla bla", null, "Pains", emptyList()),
        )
        val épiceries = mutableListOf(
                Épicerie(1, "Metro", "metro@gmail.com", "514 231-6666", produits, gabariProduits),
                Épicerie(2, "IGA", "iga@gmail.com", "514 123-4567", emptyList(), emptyList()),
                Épicerie(3, "Maxi", "maxi@gmail.com", "514 783-2759", emptyList(), emptyList()),
                Épicerie(4, "Super C", "superc@gmail.com", "514 839-2987", emptyList(),emptyList()),
        )
        val adresses = mutableListOf(
                Adresse(1, "1111", "Place Des Chocolats", "Québec", "H3A 0G4", "Canada"),
                Adresse(2, "2222", "Longue Rue", "Québec", "H1B 0N4", "Canada"),
                Adresse(3, "3333", "Rue Addison", "Ontario", "M5H 2N2", "Canada"),
                Adresse(4, "4444", "Rue Est", "Ontario", "M9B 3N4", "Canada"),
                Adresse(5, "5555", "Rue WEst", "Ontario", "K1P 5Z9", "Canada"),
        )
        val utilisateurs = mutableListOf(
               Utilisateur(1, "Montplaisir", "Samuel", "sammontplaisir@gmail.com", adresses[0], "514 123-9895"),
               Utilisateur(2, "Khakimov", "Alikhan", "akhakimov@gmail.com", adresses[1], "514 676-9823"),
               Utilisateur(3, "Tabti", "Lyazid", "lyatabti@gmail.com", adresses[2], "514 894-8268"),
               Utilisateur(4, "Vienneau", "Joël", "joelvienneau@gmail.com", adresses[3], "514 181-9135"),
               Utilisateur(5, "Lerouge", "Jean-Gabriel", "gabriellerouge@gmail.com", adresses[4], "514 112-8391"),
               Utilisateur(6, "Ligtas", "Audric", "audricligtas@gmail.com", adresses[4], "514 892-1903"),
        )
        val paniers = mutableListOf(
                Panier(1, produits[0].idProduit, 3),
                Panier(2, produits[1].idProduit, 4),
        )
        val commandes = mutableListOf(
                Commande(1, épiceries[0].idÉpicerie, utilisateurs[0].idUtilisateur, paniers[0].idPanier),
                Commande(2, épiceries[1].idÉpicerie, utilisateurs[1].idUtilisateur, paniers[0].idPanier),
                Commande(3, épiceries[2].idÉpicerie, utilisateurs[2].idUtilisateur, paniers[1].idPanier),
                Commande(4, épiceries[3].idÉpicerie, utilisateurs[3].idUtilisateur, paniers[1].idPanier),
                Commande(5, épiceries[1].idÉpicerie, utilisateurs[0].idUtilisateur, paniers[1].idPanier),
                Commande(6, épiceries[0].idÉpicerie, utilisateurs[4].idUtilisateur, paniers[1].idPanier),
                Commande(7, épiceries[0].idÉpicerie, utilisateurs[0].idUtilisateur, paniers[1].idPanier),
        )
        val livraison = mutableListOf(
            Livraison(1,commandes[0].idCommande, utilisateurs[0].idUtilisateur, adresses[0].idAdresse) ,
            Livraison(2,commandes[1].idCommande, utilisateurs[1].idUtilisateur, adresses[1].idAdresse),
            Livraison(3,commandes[2].idCommande, utilisateurs[1].idUtilisateur, adresses[2].idAdresse),

        )



        val avis = mutableListOf(
            Évaluation(1,2,"La nourriture est froide", livraison[1].code),
            Évaluation(2,5,"Impeccable", livraison[2].code),
            Évaluation(3,4,"Livraison rapide et sastifait", livraison[3].code),

        )
    }
}