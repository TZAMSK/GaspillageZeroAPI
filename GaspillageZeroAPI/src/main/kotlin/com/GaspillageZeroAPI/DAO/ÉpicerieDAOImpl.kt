package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
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
        var épicerie: Épicerie? = null

        try{
            épicerie = jdbcTemplate.queryForObject<Épicerie>("SELECT * FROM épicerie WHERE code=?", arrayOf(idÉpicerie)){resultat, _ ->
                mapRowToÉpicerie(resultat)
            }
        }catch (e: Exception){}

        return épicerie
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
                    épicerie.idAdresse,épicerie.idUtilisateur,épicerie.nom,épicerie.courriel,épicerie.téléphone,épicerie.logo)
        }catch (e: Exception){throw e}
        SourceDonnées.épiceries.add(épicerie)
        if(id!=null){
            return chercherParCode(id)
        }else{
            return null
        }
    }

    override fun supprimer(idÉpicerie: Int): Épicerie? {
        if(chercherParCode(idÉpicerie)==null){
            throw ExceptionRessourceIntrouvable("L'épicerie avec le ID $idÉpicerie est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM épicerie WHERE id=?",idÉpicerie)
        }catch(e: Exception){ throw ExceptionErreurServeur("erreur: " + e.message) }
        return null
    }

    override fun modifier(idÉpicerie: Int, épicerie: Épicerie): Épicerie? {
        try{
            jdbcTemplate.update("UPDATE épicerie SET adresse_id=?,utilisateur_code=?,nom=?,courriel=?,téléphone=?,logo=? WHERE id=?",
                    épicerie.idAdresse,épicerie.idUtilisateur,épicerie.nom,épicerie.courriel,épicerie.téléphone,épicerie.logo, idÉpicerie)
        }catch (e: Exception){throw e}
        return épicerie
    }

    private fun mapRowToÉpicerie(resultat: ResultSet): Épicerie{
        return Épicerie(
                resultat.getInt("id"),
                resultat.getInt("adresse_id"),
                resultat.getInt("utilisateur_code"),
                resultat.getString("nom"),
                resultat.getString("courriel"),
                resultat.getString("téléphone"),
                resultat.getBlob("logo")
        )
    }
}