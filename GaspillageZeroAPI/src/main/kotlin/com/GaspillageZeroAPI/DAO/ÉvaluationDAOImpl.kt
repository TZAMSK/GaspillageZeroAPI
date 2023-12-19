package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Modèle.Évaluation
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.Exception
import java.sql.ResultSet

@Repository
class ÉvaluationDAOImpl(val jdbcTemplate: JdbcTemplate) : ÉvaluationDAO {


    override fun chercherTous(): List<Évaluation> {
        return jdbcTemplate.query("SELECT * FROM avis") { rs, _ ->
            mapRowToLivraison(rs)
        }
    }

    override fun modifierÉvaluation(code: Int, avis: Évaluation): Int {
        return jdbcTemplate.update("UPDATE Évaluation SET avis = ?,  commentaire = ? WHERE code = ?",
            avis.idLivraison, avis.nbreÉtoiles, avis.message)
    }

    override fun chercherParCodeÉvaluation(code: Int): Évaluation? {
        return jdbcTemplate.query("SELECT * FROM avis WHERE idÉvaluation = ?", arrayOf(code)) { rs, _ ->
            mapRowToLivraison(rs)
        }.firstOrNull()
    }



    override fun supprimerParCodeÉvaluation(idÉvaluation: Int): Épicerie? {
        if(chercherParCodeÉvaluation(idÉvaluation)==null){
            throw ExceptionRessourceIntrouvable("L'avis dont l' ID $idÉvaluation est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM Évaluation WHERE id=?",idÉvaluation)
        }catch(e: Exception){ throw ExceptionErreurServeur("erreur: " + e.message) }
        return null
    }

    private fun mapRowToLivraison(rs: ResultSet): Évaluation {
        return Évaluation(
             idÉvaluation = rs.getInt("id"),
            idLivraison = rs.getInt("livraison_code"),
            nbreÉtoiles = rs.getInt("avis"),
            message = rs.getString("commentaire"),




        )
    }

}