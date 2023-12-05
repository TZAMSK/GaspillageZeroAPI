package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Modèle.Épicerie
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.Exception
import java.sql.ResultSet

@Repository
class ÉpicerieDAOImpl(private val jdbcTemplate: JdbcTemplate): ÉpicerieDAO {

    override fun chercherTous(): List<Épicerie>{
        return jdbcTemplate.query("SELECT * FROM Épicerie"){resultat,_ ->
            mapRowToÉpicerie(resultat)
        }
    }

    override fun chercherParCode(idÉpicerie: Int): Épicerie?{
        return try {
            jdbcTemplate.queryForObject<Épicerie>("SELECT * FROM épicerie WHERE id=?", arrayOf(idÉpicerie)){resultat, _ ->
                mapRowToÉpicerie(resultat)
            }
        }catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de la recherche de l'épicerie avec l'ID $idÉpicerie: ${e.message}")
        }
    }

    private fun obtenirProchaineIncrementationIDÉpicerie(): Int?{
        return jdbcTemplate.queryForObject("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE table_name = 'épicerie'") { resultat, _ ->
            resultat.getInt("auto_increment")
        }
    }

    override fun ajouter(épicerie: Épicerie): Épicerie? {
        val id = obtenirProchaineIncrementationIDÉpicerie()
        try{
            jdbcTemplate.update("INSERT INTO épicerie(adresse_id,utilisateur_code,nom,courriel,téléphone,logo) VALUES (?,?,?,?,?,?)",
                    épicerie.adresse?.idAdresse,épicerie.utilisateur?.code,épicerie.nom,épicerie.courriel,épicerie.téléphone,épicerie.logo)
        }catch(e: Exception){
            throw ExceptionErreurServeur("Erreur lors de l'ajout de l'épicerie: ${e.message}")
        }
        return id?.let { chercherParCode(it) }
    }

    override fun supprimer(idÉpicerie: Int): Épicerie? {
        if(chercherParCode(idÉpicerie)==null){
            throw ExceptionRessourceIntrouvable("L'épicerie avec le ID $idÉpicerie est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM épicerie WHERE id=?",idÉpicerie)
        }catch(e: Exception){
            throw ExceptionErreurServeur("Erreur lors de la suppression de l'épicerie avec l'ID $idÉpicerie: ${e.message}") }
        return null
    }

    override fun modifier(idÉpicerie: Int, épicerie: Épicerie): Épicerie? {
        try{
            jdbcTemplate.update("UPDATE épicerie SET adresse_id=?,utilisateur_code=?,nom=?,courriel=?,téléphone=?,logo=? WHERE id=?",
                    épicerie.adresse?.idAdresse,épicerie.utilisateur?.code,épicerie.nom,épicerie.courriel,épicerie.téléphone,épicerie.logo, idÉpicerie)
        }catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de la modification de l'épicerie avec l'ID $idÉpicerie: ${e.message}")
        }
        return épicerie
    }

    /*
    override fun validerÉpicerie(code_épicerie: Int, code_courant: String?): Boolean {
        val épicerie = chercherParCode(code_épicerie)

        if(épicerie?.tokenAuth0 == code_courant){
            return true
        }else{
            return false
        }
    }

     */

    private fun mapRowToÉpicerie(resultat: ResultSet): Épicerie{
        val adresseDAO = AdresseDAOImpl(jdbcTemplate)
        val utilisateurDAO = UtilisateurDAOImpl(jdbcTemplate)
        return Épicerie(
                resultat.getInt("id"),
                adresseDAO.chercherParCode(resultat.getInt("adresse_id")),
                utilisateurDAO.chercherParCode(resultat.getInt("utilisateur_code")),
                resultat.getString("nom"),
                resultat.getString("courriel"),
                resultat.getString("téléphone"),
                resultat.getBlob("logo"),
               // resultat.getString("codeAuth")
        )
    }
}