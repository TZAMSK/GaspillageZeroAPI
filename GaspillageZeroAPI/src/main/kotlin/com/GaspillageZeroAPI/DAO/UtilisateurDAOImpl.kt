package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Modèle.UtilisateursTable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class UtilisateurDAOImpl(private val jdbcTemplate: JdbcTemplate): UtilisateurDAO {

    override fun chercherTous(): List<Utilisateur> = SourceDonnées.utilisateurs
    override fun chercherParCode(idUtilisateur: Int): Utilisateur? = SourceDonnées.utilisateurs.find{it.idUtilisateur == idUtilisateur}
    override fun chercherParCodeBD(idUtilisateur: Int): UtilisateursTable? {
        return jdbcTemplate.query("SELECT * FROM Utilisateur WHERE code = ?", arrayOf(idUtilisateur)) { rs, _ ->
            mapRowToUtilisateur(rs)
        }.firstOrNull()
    }
    override fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        SourceDonnées.utilisateurs.add(utilisateur)
        return utilisateur
    }

    override fun supprimer(idUtilisateur: Int): Utilisateur? {
        val utilisateurSuppimer = SourceDonnées.utilisateurs.find { it.idUtilisateur == idUtilisateur }
        if (utilisateurSuppimer != null){
            SourceDonnées.utilisateurs.remove(utilisateurSuppimer)
        }
        return utilisateurSuppimer
    }

    override fun modifier(idUtilisateur: Int, utilisateur: Utilisateur): Utilisateur? {
        val indexModifierUtilisateur = SourceDonnées.utilisateurs.indexOf(SourceDonnées.utilisateurs.find { it.idUtilisateur == idUtilisateur })
        SourceDonnées.utilisateurs.set(indexModifierUtilisateur, utilisateur)
        return utilisateur
    }
    private fun mapRowToUtilisateur(rs: ResultSet): UtilisateursTable {
        return UtilisateursTable(
            code = rs.getInt("code"),
            nom = rs.getString("nom"),
            prénom = rs.getString("prénom"),
            courriel = rs.getString("courriel"),
            adresse_id = rs.getInt("adresse_id"),
            téléphone = rs.getString("téléphone")
        )
    }
}