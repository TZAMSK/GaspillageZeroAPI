package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Utilisateur
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class UtilisateurDAOImpl(private val jdbcTemplate: JdbcTemplate): UtilisateurDAO {

    override fun chercherTous(): List<Utilisateur> {
        return jdbcTemplate.query("SELECT * FROM utilisateur") { resultat, _ ->
            mapRowToUtilisateur(resultat)
        }
    }
    override fun chercherParCode(id: Int): Utilisateur? {
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM utilisateur WHERE code=?", arrayOf(id)){ resultat, _ ->
                mapRowToUtilisateur(resultat)
            }
        }catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de la recherche de l'utilisateur avec l'ID $id: ${e.message}")
        }
    }

    override fun validerCodeAuth0(code: Int): String? {
        return jdbcTemplate.queryForObject(
            "SELECT code_util FROM utilisateur WHERE code = ?", arrayOf(code))
        { rs, _ ->
            rs.getString("code_util")
        }
    }

    private fun obtenirProchaineIncrementationIDUtilisateur():Int?{
        return jdbcTemplate.queryForObject(
                "SELECT auto_increment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'gaspillagealimentaire' AND table_name = 'utilisateur'"
        ) { resultat, _ ->
            resultat.getInt("auto_increment")
        }
    }

    override fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        val idUtilisateur = utilisateur.code

        if (idUtilisateur != null && chercherParCode(idUtilisateur) != null) {
            throw ExceptionConflitRessourceExistante("Utilisateur existe déjà")
        }

        try {
            if (idUtilisateur != null && chercherParCode(idUtilisateur) != null) {
                throw ExceptionConflitRessourceExistante("Utilisateur existe déjà")
            }

            jdbcTemplate.update(
                    "INSERT INTO utilisateur(nom, prénom, courriel, adresse_id, téléphone, rôle) VALUES (?,?,?,?,?,?)",
                    utilisateur.nom, utilisateur.prénom, utilisateur.courriel, utilisateur.adresse?.idAdresse,
                    utilisateur.téléphone, utilisateur.rôle
            )
        } catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de l'ajout de l'utilisateur: ${e.message}")
        }

        return idUtilisateur?.let { chercherParCode(it) }
    }

    override fun supprimer(idUtilisateur: Int): Utilisateur? {
        if (chercherParCode(idUtilisateur) == null) {
            throw ExceptionRessourceIntrouvable("L'utilisateur avec le ID $idUtilisateur est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM utilisateur WHERE code=?", idUtilisateur)
        } catch (e:Exception){
            throw ExceptionErreurServeur("Erreur lors de la suppression de l'utilisateur avec l'ID $idUtilisateur: ${e.message}")
        }

        return null
    }

    override fun modifier(idUtilisateur: Int, utilisateur: Utilisateur): Utilisateur? {
        try{
            jdbcTemplate.update("UPDATE utilisateur SET nom=?, prénom=?, courriel=?, adresse_id=?, téléphone=?, rôle=? WHERE code=?",
                    utilisateur.nom, utilisateur.prénom, utilisateur.courriel, utilisateur.adresse?.idAdresse, utilisateur.téléphone, utilisateur.rôle, idUtilisateur)
        }catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de la modification de l'utilisateur avec l'ID $idUtilisateur: ${e.message}")
        }
        return utilisateur
    }

    override fun validerUtilisateur(code_utilisateur: Int, principal: String?): Boolean {
        val utilisateur = chercherParCode(code_utilisateur)

        if(utilisateur?.code_util == principal){
            return true
        }else{
            return false
        }
    }

    private fun mapRowToUtilisateur(rs: ResultSet): Utilisateur {
        val adresseDAO = AdresseDAOImpl(jdbcTemplate)

        return Utilisateur(
            code = rs.getInt("code"),
            nom = rs.getString("nom"),
            prénom = rs.getString("prénom"),
            courriel = rs.getString("courriel"),
            adresseDAO.chercherParCode(rs.getInt("adresse_id")),
            téléphone = rs.getString("téléphone"),
            rs.getString("rôle"),
            code_util = rs.getString("code_util")
        )
    }

    override fun validerDroit(utilisateur: Utilisateur?, role: String?): Boolean {
        return utilisateur?.rôle == role
    }

}