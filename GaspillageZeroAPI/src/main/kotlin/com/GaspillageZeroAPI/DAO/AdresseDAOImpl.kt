package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Adresse
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class AdresseDAOImpl(private val jdbcTemplate: JdbcTemplate): AdresseDAO {


    override fun chercherParCode(idAdresse: Int): Adresse? {
        var adresse: Adresse? = null

        try{
            adresse = jdbcTemplate.queryForObject("select * from adresse where id=?", arrayOf(idAdresse)) { resultat, _ ->
                mapRowToAdresse(resultat)
            }
        }catch (e: Exception){
            throw ExceptionRessourceIntrouvable(e.message  ?: "ressource introuvable" )
        }

        return adresse
    }

    override fun chercherTous(): List<Adresse> {
        TODO("Not yet implemented")
    }

    override fun ajouter(element: Adresse): Adresse? {
        TODO("Not yet implemented")
    }

    override fun supprimer(id: Int): Adresse? {
        TODO("Not yet implemented")
    }

    override fun modifier(id: Int, element: Adresse): Adresse? {
        TODO("Not yet implemented")
    }

    private fun mapRowToAdresse(resultat: ResultSet): Adresse{
        return Adresse(resultat.getInt("id"), resultat.getString("numéro_municipal"), resultat.getString("rue"), resultat.getString("ville"), resultat.getString("province"), resultat.getString("code_postal"), resultat.getString("pays"))
    }

}
