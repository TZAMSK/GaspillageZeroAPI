package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Exceptions.ExceptionRessourceIntrouvable
import com.GaspillageZeroAPI.Modèle.Utilisateur
import org.springframework.stereotype.Repository

@Repository
class UtilisateurDAOImpl: UtilisateurDAO {

    override fun chercherTous(): List<Utilisateur> = SourceDonnées.utilisateurs
    override fun chercherParCode(idUtilisateur: Int): Utilisateur? = SourceDonnées.utilisateurs.find{it.idUtilisateur == idUtilisateur}

    override fun ajouter(utilisateur: Utilisateur): Utilisateur? {
        SourceDonnées.utilisateurs.add(utilisateur)
        return utilisateur
    }

    override fun supprimer(idUtilisateur: Int): Utilisateur? {
        val utilisateurSuppimer = SourceDonnées.utilisateurs.find { it.idUtilisateur == idUtilisateur }
        if (utilisateurSuppimer != null){
            SourceDonnées.utilisateurs.remove(utilisateurSuppimer)
        }else{
            throw ExceptionRessourceIntrouvable("L'utilisateur avec le ID $idUtilisateur est introuvable")
        }
        return utilisateurSuppimer
    }

    override fun modifier(idUtilisateur: Int, utilisateur: Utilisateur): Utilisateur? {
        val indexModifierUtilisateur = SourceDonnées.utilisateurs.indexOf(SourceDonnées.utilisateurs.find { it.idUtilisateur == idUtilisateur })
        SourceDonnées.utilisateurs.set(indexModifierUtilisateur, utilisateur)
        return utilisateur
    }

}