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
        return jdbcTemplate.query("SELECT * FROM produits") { resultat, _ ->
            mapRowToProduit(resultat)
        }
    }

    override fun chercherParCode(idProduit: Int): Produit? {
        var produit: Produit? = null

        try {
            produit = jdbcTemplate.queryForObject<Produit>("SELECT * FROM produits WHERE id=?", arrayOf(idProduit)){resultat, _ ->
                mapRowToProduit(resultat)
            }
        }catch (e: Exception){}

        return produit
    }

    private fun obtenirProchaineIncrementationIDProduit():Int?{
        return jdbcTemplate.queryForObject(
            "SELECT auto_increment FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'gaspillagealimentaire' AND table_name = 'produits'"
        ) { resultat, _ ->
            resultat.getInt("auto_increment")
        }
    }

    override fun ajouter(produit: Produit): Produit? {
        val id = obtenirProchaineIncrementationIDProduit()
        try{
            jdbcTemplate.update("INSERT INTO produits(id, nom, date_expiration, quantité, prix, idÉpicerie, idGabarit) VALUES (?,?,?,?,?,?,?)",
                    id,produit.nom,produit.date_expiration,produit.quantité,produit.prix,produit.idÉpicerie,produit.idGabaritProduit)
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
            jdbcTemplate.update("DELETE FROM produits WHERE id=?",idProduit)
        }catch (e:Exception){throw ExceptionErreurServeur("erreur: " + e.message)}

        return null
    }

    override fun modifier(idProduit: Int, produit: Produit): Produit? {
        try{
            jdbcTemplate.update("UPDATE produits SET nom=?,date_expiration=?,quantité=?,prix=? WHERE id=?",
                    produit.nom,produit.date_expiration,produit.quantité,produit.prix,idProduit)
        }catch (e:Exception){throw e}
        return produit
    }

    override fun chercherParÉpicerie(idÉpicerie: Int): List<Produit>? {
        var produitParÉpicerie: List<Produit>? = null

        try{
            produitParÉpicerie = jdbcTemplate.query("SELECT * FROM produits WHERE idÉpicerie=?", arrayOf(idÉpicerie)) { resultat, _ ->
                mapRowToProduit(resultat)
            }
        }catch (e:Exception){}

        return produitParÉpicerie
    }

    override fun chercherParÉpicerieParCode(idÉpicerie: Int, idProduit: Int): Produit?{
        var produitParCodeParÉpicerie: Produit? = null

        try {
            produitParCodeParÉpicerie = jdbcTemplate.queryForObject<Produit>("SELECT * FROM produits WHERE idÉpicerie=? AND id=?", arrayOf(idÉpicerie,idProduit)) { resultat, _ ->
                mapRowToProduit(resultat)
            }
        }catch (e: Exception){}

        return produitParCodeParÉpicerie
    }


    private fun mapRowToProduit(resultat: ResultSet): Produit {
        return Produit(
                resultat.getInt("id"),
                resultat.getString("nom"),
                resultat.getDate("date_expiration"),
                resultat.getInt("quantité"),
                resultat.getDouble("prix"),
                resultat.getInt("idÉpicerie"),
                resultat.getInt("idGabarit")
        )
    }
}