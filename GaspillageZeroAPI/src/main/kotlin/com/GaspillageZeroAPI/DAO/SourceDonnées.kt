package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.*
import org.springframework.stereotype.Component
import java.sql.Blob
import java.util.Date

@Component
class SourceDonnées {

    companion object {


        val adresses = mutableListOf(
                Adresse(1, "1111", "Place Des Chocolats", "Montreal", "Québec", "H3A 0G4", "Canada"),
                Adresse(2, "2222", "Longue Rue", "Montreal", "Québec", "H1B 0N4", "Canada"),
                Adresse(3, "3333", "Rue Addison", "Toronto", "Ontario", "M5H 2N2", "Canada"),
                Adresse(4, "4444", "Rue Est", "Toronto", "Ontario", "M9B 3N4", "Canada"),
                Adresse(5, "5555", "Rue WEst", "Toronto", "Ontario", "K1P 5Z9", "Canada"),
        )
        val utilisateurs = mutableListOf(
                Utilisateur(1, "Montplaisir", "Samuel", "sammontplaisir@gmail.com", adresses[0], "514 123-9895", "client", "auth0|656e22be4178aefc03438594"),
                Utilisateur(2, "Khakimov", "Alikhan", "akhakimov@gmail.com", adresses[1], "514 676-9823", "client", "auth0|656e22da34408e731c3b0153"),
                Utilisateur(3, "Tabti", "Lyazid", "lyatabti@gmail.com", adresses[2], "514 894-8268", "client", "auth0|656e22ff1de2cf4321c09bf1"),
                Utilisateur(4, "Vienneau", "Joël", "joelvienneau@gmail.com", adresses[3], "514 181-9135", "client", "auth0|656e232b4178aefc0343862a"),
                Utilisateur(5, "Lerouge", "Jean-Gabriel", "gabriellerouge@gmail.com", adresses[4], "514 112-8391", "client", "auth0|656e234f1de2cf4321c09c44"),
                Utilisateur(6, "Ligtas", "Audric", "audricligtas@gmail.com", adresses[4], "514 892-1903", "client", "auth0|656e236b1de2cf4321c09c6e")
        )

        val épiceries = mutableListOf(
                Épicerie(1, adresses[1], utilisateurs[1],"Metro", "metro@gmail.com", "514 231-6666", null),
                Épicerie(2, adresses[2], utilisateurs[2],"IGA", "iga@gmail.com", "514 123-4567", null),
                Épicerie(3, adresses[3], utilisateurs[3],"Maxi", "maxi@gmail.com", "514 783-2759", null),
                Épicerie(4, adresses[4], utilisateurs[4],"Super C", "superc@gmail.com", "514 839-2987", null),
        )

        val gabariProduits = mutableListOf(
                GabaritProduit(1, "Bla bla", "Bla bla bla", null, "Légumes", épiceries[1]),
                GabaritProduit(2, "Bla bla", "Bla bla bla", null, "Pains", épiceries[2]),
        )

        val produits = mutableListOf(
                Produit(1, "Tomates", Date(2024,3,5), 230, 4.99, épiceries[1], gabariProduits[0]),
                Produit(2, "Patate", Date(2024,5,16), 24, 12.00, épiceries[1], gabariProduits[0]),
                Produit(3, "Vinaigre", Date(2024,9,23), 12, 7.00, épiceries[2], gabariProduits[1]),
                Produit(4, "Miel", Date(2024, 2,12), 24, 6.00, épiceries[3], gabariProduits[1]),
                Produit(5, "Haricots", Date(2024, 5, 19), 31, 6.50, épiceries[3], gabariProduits[0]),
                Produit(6, "Riz", Date(2023, 12, 31), 13, 8.50, épiceries[3], gabariProduits[1]),
        )

        val commandes = mutableListOf(
                Commande(1, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                )),
                Commande(2, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                )),
                Commande(3, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                )),
                Commande(4, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                )),
                Commande(5, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                )),
                Commande(6, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                )),
                Commande(7, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                )),
                Commande(8, null, null, mutableListOf<ItemsPanier>(
                        ItemsPanier(1,Produit(1, "patate", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(2,Produit(2, "celerie", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 2),
                        ItemsPanier(3,Produit(1, "jus", Date(), 33, 2.44, épiceries[1], gabariProduits[1]), 3),
                ))
        )

        val livraison = mutableListOf(
                Livraison(1, commandes[0], utilisateurs[3], adresses[3]),
                Livraison(2, commandes[1], utilisateurs[2], adresses[2]),
                Livraison(3, commandes[2], utilisateurs[3], adresses[3])
        )

        val avis = mutableListOf(
           Évaluation(1,livraison[0].code,1,"La nourriture est froide" ),
            Évaluation(2,livraison[1].code,5,"Impeccable") ,
            Évaluation(3,livraison[2].code,4,"Livraison rapide et satisfait") )
    }
}