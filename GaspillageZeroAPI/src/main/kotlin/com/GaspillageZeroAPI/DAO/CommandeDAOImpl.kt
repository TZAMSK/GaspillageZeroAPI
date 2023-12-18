package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionErreurServeur
import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet


@Repository
class CommandeDAOImpl(private val jdbcTemplate: JdbcTemplate): CommandeDAO {

    var argent = 0.0

    override fun chercherTous(): List<Commande> {
        return jdbcTemplate.query("SELECT * FROM Commande") { resultat, _ ->
            mapRowToCommande(resultat)
        }
    }

    override fun chercherParCode(idCommande: Int): Commande? {
        var commande: Commande? = null

        try {
            commande = jdbcTemplate.queryForObject<Commande>("SELECT * FROM commande WHERE code=?", arrayOf(idCommande)) { resultat, _ ->
                mapRowToCommande(resultat)
            }
        }catch (e: Exception){}

        return commande
    }

    override fun chercherCommandesParUtilisateur(idUtilisateur: Int): List<Commande>?{
        var commandesParUtilisateur: List<Commande>? = null

        try{
            commandesParUtilisateur = jdbcTemplate.query("SELECT * FROM commande WHERE utilisateur_code=?", arrayOf(idUtilisateur)) { resultat, _ ->
                mapRowToCommande(resultat)
            }
        }catch (e: Exception){ }

        return commandesParUtilisateur
    }


    override fun chercherCommandesParÉpicerie(idÉpicerie: Int): List<Commande>?{
        var commandesParÉpicerie: List<Commande>? = null

        try{
            commandesParÉpicerie = jdbcTemplate.query("SELECT * FROM commande WHERE épicerie_id=?", arrayOf(idÉpicerie)) { resultat, _ ->
                mapRowToCommande(resultat)
            }
        }catch (e: Exception){}

        return commandesParÉpicerie
    }
     


    private fun obtenireProchaineIncrementationIDCommande(): Int?{
        return jdbcTemplate.queryForObject("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE table_name = 'commande'") { resultat, _ ->
            resultat.getInt("auto_increment")
        }
    }




    override fun ajouter(commande: Commande): Commande? {
        val id = obtenireProchaineIncrementationIDCommande()
        try {
            jdbcTemplate.update("INSERT INTO commande(épicerie_id, utilisateur_code) VALUES (?, ?)",
                    commande.épicerie?.idÉpicerie, commande.utilisateur?.code)
            for(itemPanier in commande.panier){
                jdbcTemplate.update("INSERT INTO commande_produits(commande_code, produit_id, quantité) values((select code from commande order by code desc limit 1),?,?)",
                        itemPanier.produit.idProduit, itemPanier.quantité)
            }
        }catch (e: Exception){ throw e }
        if(id!=null){
            return chercherParCode(id)
        }else{
            return null
        }

    }

    override fun supprimer(idCommande: Int): Commande? {
        if(chercherParCode(idCommande)==null){
            throw ExceptionRessourceIntrouvable("La commande avec le ID $idCommande est introuvable")
        }
        try{
            jdbcTemplate.update("DELETE FROM commande_produits WHERE commande_code=?", idCommande)
            jdbcTemplate.update("DELETE FROM commande WHERE code=?", idCommande)
        }catch(e: Exception){ throw ExceptionErreurServeur("erreur: " + e.message) }

        return null
    }

    override fun modifier(idCommande: Int, commande: Commande): Commande? {
        try{
            jdbcTemplate.update("UPDATE commande SET épicerie_id=?, utilisateur_code=? WHERE code=?",
                    commande.épicerie?.idÉpicerie, commande.utilisateur?.code, idCommande)
            for(itemPanier in commande.panier){
                jdbcTemplate.update("UPDATE commande_produits SET  quantité=? WHERE commande_code=? AND produit_id=?",
                        itemPanier.quantité, idCommande, itemPanier.produit.idProduit)
            }
        }catch (e: Exception){throw e}
        return commande
    }

    fun chercherItemsPanierParCodeCommande(code: Int): MutableList<ItemsPanier>?{
        var panier: MutableList<ItemsPanier>? = null
        try{
            panier = jdbcTemplate.query("select produits.id, produits.nom, produits.date_expiration, produits.quantité as quantité_produit, produits.prix, produits.idÉpicerie, produits.idGabarit, commande_produits.quantité from commande_produits join produits on commande_produits.produit_id = produits.id where commande_code = ?", arrayOf(code) ) {resultat, _ ->
                mapRowToItemPanier(resultat)
            }
        }catch (e: Exception){}
        return panier
    }

    private fun mapRowToCommande(resultat: ResultSet): Commande {
        val épicerieDAO = ÉpicerieDAOImpl(jdbcTemplate)
        val utilisateurDAO = UtilisateurDAOImpl(jdbcTemplate)

        val commande = Commande(
                resultat.getInt("code"),
                épicerieDAO.chercherParCode(resultat.getInt("épicerie_id")),
                utilisateurDAO.chercherParCode(resultat.getInt("utilisateur_code")),
                chercherItemsPanierParCodeCommande(resultat.getInt("code")) ?: mutableListOf()
        )
        return commande
    }

    private fun mapRowToItemPanier(resultat: ResultSet):ItemsPanier? {
        var itemPanier: ItemsPanier? = null
        val épicerieDAO = ÉpicerieDAOImpl(jdbcTemplate)
        val gabaritProduitDAO = GabaritProduitDAOImpl(jdbcTemplate)

        itemPanier  = ItemsPanier(
            Produit(
                    resultat.getInt("id"),
                    resultat.getString("nom"),
                    resultat.getDate("date_expiration"),
                    resultat.getInt("quantité_produit"),
                    resultat.getDouble("prix"),
                    épicerieDAO.chercherParCode(resultat.getInt("idÉpicerie")),
                    gabaritProduitDAO.chercherParCode(resultat.getInt("idGabarit"))
            ),
            resultat.getInt("quantité")
        )

        print(itemPanier)
        return itemPanier
    }

}