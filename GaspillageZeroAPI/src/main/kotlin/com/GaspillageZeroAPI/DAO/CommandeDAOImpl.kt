package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionConflitRessourceExistante
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
            commande = jdbcTemplate.queryForObject<Commande>("SELECT * FROM commande WHERE code=?", arrayOf(idCommande)){ resultat, _ ->
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

    override fun chercherCommandeParUtilisateur(idUtilisateur: Int, idCommande: Int): Commande? = SourceDonnées.commandes.find{it.idUtilisateur == idUtilisateur && it.idCommande == idCommande}

    override fun chercherCommandeParÉpicerie(idÉpicerie: Int, idCommande: Int): Commande? = SourceDonnées.commandes.find{it.idÉpicerie == idÉpicerie && it.idCommande == idCommande}

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
                    commande.idÉpicerie, commande.idUtilisateur)
            for(itemPanier in commande.panier){
                jdbcTemplate.update("INSERT INTO commande_produits(commande_code, produit_id, quantité) values(?,?,?)",
                        id, itemPanier.produit, itemPanier.quantité)
            }
        }catch (e: Exception){ throw e }
        SourceDonnées.commandes.add(commande)
        if(id!=null){
            return chercherParCode(id)
        }else{
            return null
        }

    }

    override fun supprimer(idCommande: Int): Commande? {
        val commandeSuppimer = SourceDonnées.commandes.find { it.idCommande == idCommande }

        try{
            jdbcTemplate.update("DELETE FROM commande WHERE code=?", idCommande)
        }catch(e: Exception){}

        return commandeSuppimer
    }

    override fun modifier(idCommande: Int, commande: Commande): Commande? {
        try{
            jdbcTemplate.update("UPDATE commande SET épicerie_id=?, utilisateur_code=? WHERE code=?",
                    commande.idÉpicerie, commande.idUtilisateur, idCommande)
            for(itemPanier in commande.panier){
                jdbcTemplate.update("UPDATE commande_produits SET  quantité=? WHERE commande_code=? AND produit_id=?",
                        itemPanier.quantité, idCommande, itemPanier.produit)
            }
        }catch (e: Exception){throw e}
        return commande
    }

    fun chercherItemsPanierParCodeCommande(code: Int): MutableList<ItemsPanier>?{
        var panier: MutableList<ItemsPanier>? = null

        try{
            panier = jdbcTemplate.query("SELECT produit_id, quantité FROM commande_produits WHERE commande_code=?", arrayOf(code)) {resultat, _ ->
                mapRowToItemPanier(resultat)
            }
        }catch (e: Exception){}

        return panier
    }

    private fun mapRowToCommande(resultat: ResultSet): Commande {
        return Commande(
                resultat.getInt("code"),
                resultat.getInt("épicerie_id"),
                resultat.getInt("utilisateur_code"),
                chercherItemsPanierParCodeCommande(resultat.getInt("code")) ?: mutableListOf()
        )
    }

    private fun mapRowToItemPanier(resultat: ResultSet):ItemsPanier{
        return ItemsPanier(
                resultat.getInt("produit_id"),
                resultat.getInt("quantité")
        )
    }
}