package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.*
import org.springframework.stereotype.Component
import java.sql.Blob
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
                GabaritProduit(1, "Bla bla", "Bla bla bla", null, "Légumes", 1),
                GabaritProduit(2, "Bla bla", "Bla bla bla", null, "Pains", 2),
        )
        val épiceries = mutableListOf(
                Épicerie(1, 1,1,"Metro", "metro@gmail.com", "514 231-6666", null),
                Épicerie(2, 2,2,"IGA", "iga@gmail.com", "514 123-4567", null),
                Épicerie(3, 3,3,"Maxi", "maxi@gmail.com", "514 783-2759", null),
                Épicerie(4, 4,4,"Super C", "superc@gmail.com", "514 839-2987", null),
        )
        val adresses = mutableListOf(
                Adresse(1, "1111", "Place Des Chocolats", "Montreal", "Québec", "H3A 0G4", "Canada"),
                Adresse(2, "2222", "Longue Rue", "Montreal", "Québec", "H1B 0N4", "Canada"),
                Adresse(3, "3333", "Rue Addison", "Toronto", "Ontario", "M5H 2N2", "Canada"),
                Adresse(4, "4444", "Rue Est", "Toronto", "Ontario", "M9B 3N4", "Canada"),
                Adresse(5, "5555", "Rue WEst", "Toronto", "Ontario", "K1P 5Z9", "Canada"),
        )
        val utilisateurs = mutableListOf(
                Utilisateur(1, "Montplaisir", "Samuel", "sammontplaisir@gmail.com", adresses[0], "514 123-9895", mutableListOf(
                        Utilisateur_Rôle(1, "client", Date())
                )),
                Utilisateur(2, "Khakimov", "Alikhan", "akhakimov@gmail.com", adresses[1], "514 676-9823", mutableListOf(
                        Utilisateur_Rôle(2, "client", Date())
                )),
                Utilisateur(3, "Tabti", "Lyazid", "lyatabti@gmail.com", adresses[2], "514 894-8268", mutableListOf(
                        Utilisateur_Rôle(3, "client", Date())
                )),
                Utilisateur(4, "Vienneau", "Joël", "joelvienneau@gmail.com", adresses[3], "514 181-9135", mutableListOf(
                        Utilisateur_Rôle(4, "client", Date())
                )),
                Utilisateur(5, "Lerouge", "Jean-Gabriel", "gabriellerouge@gmail.com", adresses[4], "514 112-8391", mutableListOf(
                        Utilisateur_Rôle(5, "client", Date())
                )),
                Utilisateur(6, "Ligtas", "Audric", "audricligtas@gmail.com", adresses[4], "514 892-1903", mutableListOf(
                        Utilisateur_Rôle(6, "client", Date())
                ))
        )

        val commandes = mutableListOf(
                Commande(1, épiceries[0].idÉpicerie, utilisateurs[0].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                )),
                Commande(2, épiceries[1].idÉpicerie, utilisateurs[1].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                )),
                Commande(3, épiceries[2].idÉpicerie, utilisateurs[2].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                )),
                Commande(4, épiceries[3].idÉpicerie, utilisateurs[3].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                )),
                Commande(5, épiceries[1].idÉpicerie, utilisateurs[0].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                )),
                Commande(6, épiceries[0].idÉpicerie, utilisateurs[4].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                )),
                Commande(7, épiceries[0].idÉpicerie, utilisateurs[0].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                )),
                Commande(8, épiceries[1].idÉpicerie, utilisateurs[0].idUtilisateur, mutableListOf<ItemsPanier>(
                        ItemsPanier(1, 2),
                        ItemsPanier(2, 2),
                        ItemsPanier(4, 3),
                ))
        )

    }
}