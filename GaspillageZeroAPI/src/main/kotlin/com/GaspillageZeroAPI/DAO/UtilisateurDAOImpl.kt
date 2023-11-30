package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Modèle.UtilisateursTable
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
        var utilisateur: Utilisateur? = null

        try {
            utilisateur = jdbcTemplate.queryForObject<Utilisateur>("SELECT * FROM utilisateur WHERE code=?", arrayOf(id)){ resultat, _ ->
                mapRowToUtilisateur(resultat)
            }
        }catch (e: Exception){}

        return utilisateur
    }

    private fun obtenirProchaineIncrementationIDUtilisateur():Int?{
        return jdbcTemplate.queryForObject(
                "SELECT auto_increment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'gaspillagealimentaire' AND table_name = 'utilisateur'"
        ) { resultat, _ ->
            resultat.getInt("auto_increment")
        }
    }

    override fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        val id = obtenirProchaineIncrementationIDUtilisateur()
        try{
            jdbcTemplate.update("INSERT INTO utilisateur(nom, prénom, courriel, adresse_id, téléphone, rôle) VALUES (?,?,?,?,?,?)",
                    utilisateur.nom, utilisateur.prénom, utilisateur.courriel, utilisateur.adresse?.idAdresse, utilisateur.téléphone, utilisateur.rôle)
        }catch(e: java.lang.Exception){throw e}
        if(id!=null) {
            return chercherParCode(id)
        }else {
            return null
        }
    }

    override fun supprimer(idUtilisateur: Int): Utilisateur? {
        if(chercherParCode(idUtilisateur)==null){
            throw ExceptionRessourceIntrouvable("L'utilisateur avec le ID $idUtilisateur est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM utilisateur WHERE code=?", idUtilisateur)
        } catch (e:Exception){
            throw ExceptionErreurServeur("erreur: " + e.message)
        }

        return null
    }

    override fun modifier(idUtilisateur: Int, utilisateur: Utilisateur): Utilisateur? {
        try{
            jdbcTemplate.update("UPDATE utilisateur SET nom=?, prénom=?, courriel=?, adresse_id=?, téléphone=?, rôle=? WHERE code=?",
                    utilisateur.nom, utilisateur.prénom, utilisateur.courriel, utilisateur.adresse?.idAdresse, utilisateur.téléphone, utilisateur.rôle, idUtilisateur)
        }catch (e:Exception){throw e}
        return utilisateur
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
            rs.getString("rôle")
        )
    }
}