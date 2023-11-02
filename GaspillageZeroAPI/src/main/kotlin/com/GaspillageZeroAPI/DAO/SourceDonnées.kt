package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Épicerie
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
        val épiceries = mutableListOf(
                Épicerie(1, "Metro", "metro@gmail.com", "514 231-6666", produits),
                Épicerie(2, "IGA", "iga@gmail.com", "514 123-4567", emptyList()),
                Épicerie(3, "Maxi", "maxi@gmail.com", "514 783-2759", emptyList()),
                Épicerie(4, "Super C", "superc@gmail.com", "514 839-2987", emptyList()),
        )
    }
}