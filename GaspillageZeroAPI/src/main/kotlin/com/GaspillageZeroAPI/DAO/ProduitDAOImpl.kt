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
        return try {
            jdbcTemplate.queryForObject("SELECT * FROM produits WHERE id=?", arrayOf(idProduit)){resultat, _ ->
                mapRowToProduit(resultat)
            }
        }catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de la recherche du produit avec l'ID $idProduit: ${e.message}")
        }
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
            jdbcTemplate.update("INSERT INTO produits(nom, date_expiration, quantité, prix, idÉpicerie, idGabarit) VALUES (?,?,?,?,?,?)",
                    produit.nom,produit.date_expiration,produit.quantité,produit.prix,produit.épicerie?.idÉpicerie,produit.gabaritProduit?.idGabaritProduit)
        }catch(e: Exception){
            throw ExceptionErreurServeur("Erreur lors de l'ajout du produit: ${e.message}")
        }
        return id?.let { chercherParCode(it) }
    }

    override fun supprimer(idProduit: Int): Produit? {
        if(chercherParCode(idProduit)==null){
            throw ExceptionRessourceIntrouvable("Le produit avec le ID $idProduit est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM commande_produits WHERE produit_id=?", idProduit)
            jdbcTemplate.update("DELETE FROM produits WHERE id=?",idProduit)
        }catch (e:Exception){throw ExceptionErreurServeur("Erreur lors de la suppression du produit avec l'ID $idProduit: ${e.message}")}
        return null
    }

    override fun modifier(idProduit: Int, produit: Produit): Produit? {
        try{
            jdbcTemplate.update("UPDATE produits SET nom=?,date_expiration=?,quantité=?,prix=?,idÉpicerie=?,idGabarit=? WHERE id=?",
                    produit.nom,produit.date_expiration,produit.quantité,produit.prix,produit.épicerie?.idÉpicerie,produit.gabaritProduit?.idGabaritProduit,idProduit)
        }catch (e: Exception) {
            throw ExceptionErreurServeur("Erreur lors de la modification du produit avec l'ID $idProduit: ${e.message}")
        }
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

    override fun estGerantParCode(code: String): Boolean {
        val utilisateurDAO = UtilisateurDAOImpl(jdbcTemplate)
        val utilisateurs = utilisateurDAO.chercherTous()
        val utilisateur = utilisateurs.find { it.tokenAuth0 == code }
        return utilisateur?.rôle?.contains("épicerie") ?: false
    }


    private fun mapRowToProduit(resultat: ResultSet): Produit {
        val épicerieDAO = ÉpicerieDAOImpl(jdbcTemplate)
        val gabaritDAO = GabaritProduitDAOImpl(jdbcTemplate)
        return Produit(
                resultat.getInt("id"),
                resultat.getString("nom"),
                resultat.getDate("date_expiration"),
                resultat.getInt("quantité"),
                resultat.getDouble("prix"),
                épicerieDAO.chercherParCode(resultat.getInt("idÉpicerie")),
                gabaritDAO.chercherParCode(resultat.getInt("idGabarit"))
        )
    }
}