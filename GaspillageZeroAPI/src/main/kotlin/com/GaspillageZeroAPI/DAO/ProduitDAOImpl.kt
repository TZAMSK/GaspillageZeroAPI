package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import java.lang.Exception
import java.sql.ResultSet


@Repository
class ProduitDAOImpl(private val jdbcTemplate: JdbcTemplate): ProduitDAO {


    override fun chercherTous(): List<Produit> {
        return jdbcTemplate.query("SELECT * FROM Produit") { resultat, _ ->
            mapRowToProduit(resultat)
        }
    }

    override fun chercherParCode(idProduit: Int): Produit? {
        var produit: Produit? = null

        try {
            produit = jdbcTemplate.queryForObject<Produit>("SELECT * FROM produit WHERE code=?", arrayOf(idProduit)){resultat, _ ->
                mapRowToProduit(resultat)
            }
        }catch (e: Exception){}

        return produit
    }

    private fun obtenirProchaineIncrementationIDProduit():Int?{
        return jdbcTemplate.queryForObject("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES\n" +
        "WHERE table_name = 'produit'"){ resultat, _ ->
            resultat.getInt("auto_increment")
        }
    }

    override fun ajouter(produit: Produit): Produit? {
        val id = obtenirProchaineIncrementationIDProduit()
        try{
            jdbcTemplate.update("INSERT INTO produit(nom,date_expiration,quantité,prix) VALUES (?,?,?,?)",
                    produit.nom,produit.date_expiration,produit.quantité,produit.prix)
        }catch(e: Exception){throw e}
        SourceDonnées.produits.add(produit)
        if(id!=null) {
            return chercherParCode(id)
        }else {
            return null
        }
    }

    override fun supprimer(idProduit: Int): Produit? {
        if(chercherParCode(idProduit)==null){
            throw ExceptionRessourceIntrouvable("Le produit avec le ID $idProduit est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM produit WHERE id=?",idProduit)
        }catch (e:Exception){throw ExceptionErreurServeur("erreur: " + e.message)}

        return null
    }

    override fun modifier(idProduit: Int, produit: Produit): Produit? {
        try{
            jdbcTemplate.update("UPDATE produit SET nom=?,date_expiration=?,quantité=?,prix=? WHERE id=?",
                    produit.nom,produit.date_expiration,produit.quantité,produit.prix,idProduit)
        }catch (e:Exception){throw e}
        return produit
    }

    override fun chercherParÉpicerie(idÉpicerie: Int): List<Produit>? {
        var produitParÉpicerie: List<Produit>? = null

        try{
            produitParÉpicerie = jdbcTemplate.query("SELECT * FROM produit WHERE idÉpicerie=?", arrayOf(idÉpicerie)) { resultat, _ ->
                mapRowToProduit(resultat)
            }
        }catch (e:Exception){}

        return produitParÉpicerie
    }

    override fun chercherParÉpicerieParCode(idÉpicerie: Int, idProduit: Int): Produit?{
        var produitParCodeParÉpicerie: Produit? = null

        try {
            produitParCodeParÉpicerie = jdbcTemplate.queryForObject<Produit>("SELECT * FROM produit WHERE idÉpicerie=? AND idProduit=?", arrayOf(idProduit)) { resultat, _ ->
                mapRowToProduit(resultat)
            }
        }catch (e: Exception){}

        return produitParCodeParÉpicerie
    }


    private fun mapRowToProduit(resultat: ResultSet): Produit {
        return Produit(
                resultat.getInt("code"),
                resultat.getString("nom"),
                resultat.getDate("date_expiration"),
                resultat.getInt("quantité"),
                resultat.getDouble("prix"),
        )
    }
}