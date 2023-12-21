package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.ItemsPanier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository

class ItemsPanierDAOImpl(private val jdbcTemplate: JdbcTemplate): ItemsPanierDAO {

    override fun chercherTous(): List<ItemsPanier> {
        return jdbcTemplate.query("SELECT * FROM commande_produits") { resultat, _ ->
            mapRowToItemsPanier(resultat)
        }
    }

    override fun chercherParCode(idItemsPanier: Int): ItemsPanier? {
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM commande_produits WHERE commande_code=?", arrayOf(idItemsPanier)) { resultSet, _ ->
                mapRowToItemsPanier(resultSet)
            }
        } catch (e: java.lang.Exception) {
            throw ExceptionErreurServeur("Erreur lors de la recherche du gabarit produit avec l'ID $idItemsPanier: ${e.message}")
        }
    }

    override fun supprimer(idItemsPanier: Int): ItemsPanier? {
        if(chercherParCode(idItemsPanier)==null){
            throw ExceptionRessourceIntrouvable("La commande produits avec le ID $idItemsPanier est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM commande_produits WHERE commande_code=?", idItemsPanier)
            //jdbcTemplate.update("DELETE FROM commande WHERE code=?", idItemsPanier)
        }catch(e: Exception){ throw ExceptionErreurServeur("erreur: " + e.message) }
        return null
    }

    override fun estGerantParCode(code: String): Boolean {
        val utilisateurDAO = UtilisateurDAOImpl(jdbcTemplate)
        val utilisateurs = utilisateurDAO.chercherTous()
        val utilisateur = utilisateurs.find { it.tokenAuth0 == code }
        return utilisateur?.rôle?.contains("épicerie") ?: false
    }
    override fun ajouter(element: ItemsPanier): ItemsPanier? {
        TODO("Not yet implemented")
    }

    override fun modifier(id: Int, element: ItemsPanier): ItemsPanier? {
        TODO("Not yet implemented")
    }

    private fun mapRowToItemsPanier(resultat: ResultSet): ItemsPanier {
        //val commandeDAO = CommandeDAOImpl(jdbcTemplate)
        val produitDAO = ProduitDAOImpl(jdbcTemplate)

        val itemsPanier = ItemsPanier(
            resultat.getInt("commande_code"),
            produitDAO.chercherParCode(resultat.getInt("produit_id")),
            resultat.getInt("quantité")
        )
        return itemsPanier
    }
}