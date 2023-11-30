package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.GabaritProduit
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.lang.Exception
import java.sql.ResultSet

@Repository
class GabaritProduitDAOImpl(private val jdbcTemplate: JdbcTemplate): GabaritProduitDAO {

    override fun chercherTous(): List<GabaritProduit> {
        return jdbcTemplate.query("SELECT * FROM gabaritproduit"){resultat, _ ->
            mapRowToGabaritProduit(resultat)
        }
    }

    override fun chercherParCode(idGabaritProduit: Int): GabaritProduit?{
        var gabaritProduit : GabaritProduit? = null

        try {
            gabaritProduit = jdbcTemplate.queryForObject<GabaritProduit>("SELECT * FROM gabaritproduit WHERE id=?", arrayOf(idGabaritProduit)){resultat, _ ->
                mapRowToGabaritProduit(resultat)
            }
        }catch (e: Exception){}

        return gabaritProduit
    }

    private fun obtenirProchaineIncrementationIDGabaritProduit():Int?{
        return jdbcTemplate.queryForObject("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE table_name = 'gabaritproduit'"){ resultat, _ ->
            resultat.getInt("auto_increment")
        }
    }

    override fun ajouter(gabaritProduit: GabaritProduit): GabaritProduit?{
        val id = obtenirProchaineIncrementationIDGabaritProduit()
        try{
            jdbcTemplate.update("INSERT INTO gabaritproduit(nom,description,image,catégorie,idÉpicerie) VALUES (?,?,?,?,?)",
                    gabaritProduit.nom,gabaritProduit.description,gabaritProduit.image,gabaritProduit.categorie,gabaritProduit.épicerie?.idÉpicerie)
        }catch(e: Exception){throw e}
        SourceDonnées.gabariProduits.add(gabaritProduit)
        if(id!=null){
            return chercherParCode(id)
        }else{
            return null
        }
    }

    override fun supprimer(idGabaritProduit: Int): GabaritProduit? {
        if(chercherParCode(idGabaritProduit)==null){
            throw ExceptionRessourceIntrouvable("Le gabaritproduit avec le ID $idGabaritProduit est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM gabaritproduit WHERE id=?", idGabaritProduit)
        }catch (e: Exception){throw ExceptionErreurServeur("erreur: " + e.message) }
        return null
    }

    override fun modifier(idGabaritProduit: Int, gabaritProduit: GabaritProduit): GabaritProduit? {
        try {
            jdbcTemplate.update("UPDATE gabaritproduit SET nom=?,description=?,image=?,catégorie=?,idÉpicerie=? WHERE id=?",
                    gabaritProduit.nom,gabaritProduit.description,gabaritProduit.image,gabaritProduit.categorie,gabaritProduit.épicerie?.idÉpicerie,idGabaritProduit)
        }catch (e: Exception){throw e}
        return gabaritProduit
    }

    private fun mapRowToGabaritProduit(resultat: ResultSet):GabaritProduit{
        val épicerieDAO = ÉpicerieDAOImpl(jdbcTemplate)
        return GabaritProduit(
                resultat.getInt("id"),
                resultat.getString("nom"),
                resultat.getString("description"),
                resultat.getBlob("image"),
                resultat.getString("catégorie"),
                épicerieDAO.chercherParCode(resultat.getInt("idÉpicerie"))
        )
    }
}