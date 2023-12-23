package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
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

    override fun chercherParUtilisateurCommandeEtLivraison(code: Int, commande_code: Int, utilisateur_code: String?, livraison_code: Int): Livraison? {
        return jdbcTemplate.queryForObject(
            "SELECT" +
                    " l.code, " +
                    " l.commande_code, " +
                    " l.utilisateur_code, " +
                    " l.adresse_id " +
                    " FROM " +
                    " livraison l " +
                    " JOIN" +
                    " commande c ON l.commande_code = c.code " +
                    " JOIN " +
                    " utilisateur u ON c.utilisateur_code = u.code " +
                    " WHERE u.code_util = ? " +
                    " AND u.code = ? " +
                    " AND c.code = ? " +
                    " AND l.code = ?",
            { rs, _ -> mapRowToLivraison(rs) },
            utilisateur_code,
            code,
            commande_code,
            livraison_code
        )
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
            id, livraison.commande?.idCommande, livraison.utilisateur?.code, livraison.adresse?.idAdresse)
        } catch (e: DataIntegrityViolationException){
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur lors de l'ajout de la livraison: ${e.localizedMessage}")
        }
        return livraison
    }

    override fun modifier(code: Int, livraison: Livraison): Livraison? {
        try {
            jdbcTemplate.update(
                "UPDATE Livraison SET commande_code = ?, utilisateur_code = ?, adresse_id = ? WHERE code = ?",
                livraison.commande?.idCommande, livraison.utilisateur?.code, livraison.adresse?.idAdresse, code)
        }catch (e: DataIntegrityViolationException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur lors de l'ajout de la livraison: ${e.localizedMessage}")
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

        val commandeDAO = CommandeDAOImpl(jdbcTemplate)
        val utilisateurDAO = UtilisateurDAOImpl(jdbcTemplate)
        val adresseDAO = AdresseDAOImpl(jdbcTemplate)
        return Livraison(
            code = rs.getInt("code"),
            commandeDAO.chercherParCode(rs.getInt("commande_code")),
            utilisateurDAO.chercherParCode(rs.getInt("utilisateur_code")),
            adresseDAO.chercherParCode(rs.getInt("adresse_id"))
        )
    }
}