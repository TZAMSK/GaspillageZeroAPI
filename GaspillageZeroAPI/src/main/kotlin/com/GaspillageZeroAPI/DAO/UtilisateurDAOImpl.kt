package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Utilisateur
import com.GaspillageZeroAPI.Modèle.UtilisateursTable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class UtilisateurDAOImpl(private val jdbcTemplate: JdbcTemplate): UtilisateurDAO {

    override fun chercherTous(): List<Utilisateur> = SourceDonnées.utilisateurs
    //override fun chercherParCode(idUtilisateur: Int): Utilisateur? = SourceDonnées.utilisateurs.find{it.idUtilisateur == idUtilisateur}
    override fun chercherParCode(id: Int): Utilisateur? {
        return jdbcTemplate.queryForObject("SELECT * from Utilisateur WHERE code = ?", arrayOf(id)) { resultat, _ ->
            mapRowToUtilisateur(resultat)
        }
    }

    override fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        SourceDonnées.utilisateurs.add(utilisateur)
        return utilisateur
    }

    override fun supprimer(idUtilisateur: Int): Utilisateur? {
        val utilisateurSuppimer = SourceDonnées.utilisateurs.find { it.code == idUtilisateur }
        if (utilisateurSuppimer != null){
            SourceDonnées.utilisateurs.remove(utilisateurSuppimer)
        }else{
            throw ExceptionRessourceIntrouvable("L'utilisateur avec le ID $idUtilisateur est introuvable")
        }
        return utilisateurSuppimer
    }

    override fun modifier(idUtilisateur: Int, utilisateur: Utilisateur): Utilisateur? {
        val indexModifierUtilisateur = SourceDonnées.utilisateurs.indexOf(SourceDonnées.utilisateurs.find { it.code == idUtilisateur })
        SourceDonnées.utilisateurs.set(indexModifierUtilisateur, utilisateur)
        return utilisateur
    }
    private fun mapRowToUtilisateur(rs: ResultSet): Utilisateur {
        val adresseDAO = AdresseDAOImpl(jdbcTemplate)

        return Utilisateur(
            code = rs.getInt("code"),
            nom = rs.getString("nom"),
            prénom = rs.getString("prénom"),
            courriel = rs.getString("courriel"),
            adresseDAO.chercherParCode(rs.getInt("adresse_id")),
            téléphone = rs.getString("téléphone"),
            rs.getString("rôle")
        )
    }
}