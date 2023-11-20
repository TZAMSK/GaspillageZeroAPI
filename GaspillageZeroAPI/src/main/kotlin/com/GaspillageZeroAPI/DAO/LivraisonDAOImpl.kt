package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Livraison
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class LivraisonDAOImpl(private val jdbcTemplate: JdbcTemplate): LivraisonDAO {

    override fun chercherTous(): List<Livraison> {
        return jdbcTemplate.query("SELECT * FROM Livraison") { rs, _ ->
            mapRowToLivraison(rs)
        }
    }

    override fun chercherParCode(code: Int): Livraison? {
        return jdbcTemplate.query("SELECT * FROM Livraison WHERE code = ?", arrayOf(code)) { rs, _ ->
            mapRowToLivraison(rs)
        }.firstOrNull()
    }

    override fun ajouter(livraison: Livraison): Int {
        return jdbcTemplate.update("INSERT INTO Livraison(code, commande_code, utilisateur_code, adresse_id) VALUES (?, ?, ?, ?)",
            livraison.code, livraison.commande_code, livraison.utilisateur_code, livraison.adresse_id)
    }

    override fun modifier(code: Int, livraison: Livraison): Int {
        return jdbcTemplate.update("UPDATE Livraison SET commande_code = ?, utilisateur_code = ?, adresse_id = ? WHERE code = ?",
            livraison.code, livraison.commande_code, livraison.utilisateur_code, livraison.adresse_id)
    }

    override fun supprimer(code: Int): Int {
        return jdbcTemplate.update("DELETE FROM Livraison WHERE code = ?", code)
    }

    private fun mapRowToLivraison(rs: ResultSet): Livraison {
        return Livraison(
            code = rs.getInt("code"),
            commande_code = rs.getInt("commande_code"),
            utilisateur_code = rs.getInt("utilisateur_code"),
            adresse_id = rs.getInt("adresse_id")
        )
    }
}