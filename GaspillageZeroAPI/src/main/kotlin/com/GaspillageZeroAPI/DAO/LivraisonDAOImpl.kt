package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
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

        return try {
            jdbcTemplate.queryForObject("SELECT * FROM Livraison WHERE code = ?", arrayOf(code)) { rs, _ ->
                mapRowToLivraison(rs)
            }
        } catch (e: java.lang.Exception) {
            throw ExceptionErreurServeur("Erreur lors de la recherche de la livraison avec l'ID $code: ${e.message}")
        }
    }

    override fun chercherLivraisonExistanteParCode(code: Int): Int? {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT * FROM livraison WHERE code = ?) AS livraisonExistante", arrayOf(code))
        { rs, _ ->
                rs.getInt("livraisonExistante")
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
        } catch (e:Exception){
            throw ExceptionErreurServeur("Erreur lors de l'ajout de la livraison: ${e.message}")
        }
        return livraison
    }

    override fun modifier(code: Int, livraison: Livraison): Livraison? {
        try {
            jdbcTemplate.update(
                "UPDATE Livraison SET commande_code = ?, utilisateur_code = ?, adresse_id = ? WHERE code = ?",
                livraison.commande_code, livraison.utilisateur_code, livraison.adresse_id, code)
        }catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de la modification de la livraison avec l'ID $code: ${e.message}")
        }
        return livraison
    }
    //override fun chercherLivraisonParÉvaluation(idÉvaluation: Int, idLivrainson: Int): Évaluation? = SourceDonnées.avis.find{it.idÉvaluation == idÉvaluation && it.idLivraison == idLivrainson}

    override fun supprimer(code: Int): Livraison? {
        if(chercherParCode(code) == null){
            throw ExceptionRessourceIntrouvable("La commande avec le code $code est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM Livraison WHERE code = ?", code)
        } catch (e:Exception){
            throw ExceptionErreurServeur("Erreur lors de la suppression de la livraison avec l'ID $code: ${e.message}")
        }

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