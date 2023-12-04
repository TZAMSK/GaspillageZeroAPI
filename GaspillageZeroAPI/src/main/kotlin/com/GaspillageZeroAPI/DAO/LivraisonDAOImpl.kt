package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Commande
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Évaluation
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class LivraisonDAOImpl(val jdbcTemplate: JdbcTemplate): LivraisonDAO {

    override fun chercherTous(): List<Livraison> {
        return jdbcTemplate.query("SELECT * FROM Livraison") { rs, _ ->
            mapRowToLivraison(rs)
        }
    }

    override fun chercherParCode(code: Int): Livraison? {

        return jdbcTemplate.queryForObject<Livraison>(
            "SELECT * FROM Livraison WHERE code = ?",
            arrayOf(code)
        ) { rs, _ ->
            mapRowToLivraison(rs)
        }
    }

    private fun obtenirProchaineIncrementationIDLivraison(): Int? {
        return jdbcTemplate.queryForObject("SELECT COALESCE(MAX(code), 0) + 1 AS max_code FROM Livraison")
        { resultat, _ ->
            resultat.getInt("max_code")
        }
    }

    override fun ajouter(livraison: Livraison): Livraison? {
        var id = obtenirProchaineIncrementationIDLivraison()
        try {
            jdbcTemplate.update("INSERT INTO Livraison(code, commande_code, utilisateur_code, adresse_id) VALUES (?, ?, ?, ?)",
            id, livraison.commande_code, livraison.utilisateur_code, livraison.adresse_id)
        } catch (e:Exception){ throw e }
        if(id != null && id != 0){
            return chercherParCode(id)
        } else {
            return null
        }
    }

    override fun modifier(code: Int, livraison: Livraison): Livraison? {
        try {
            jdbcTemplate.update(
                "UPDATE Livraison SET commande_code = ?, utilisateur_code = ?, adresse_id = ? WHERE code = ?",
                livraison.commande_code, livraison.utilisateur_code, livraison.adresse_id, code)
        } catch (e:Exception){throw e}

        return livraison
    }
    //override fun chercherLivraisonParÉvaluation(idÉvaluation: Int, idLivrainson: Int): Évaluation? = SourceDonnées.avis.find{it.idÉvaluation == idÉvaluation && it.idLivraison == idLivrainson}

    override fun supprimer(code: Int): Livraison? {
        if(chercherParCode(code) == null){
            throw ExceptionRessourceIntrouvable("La commande avec le code $code est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM Livraison WHERE code = ?", code)
        } catch (e:Exception){throw e}

        return null
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