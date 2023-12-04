package com.GaspillageZeroAPI.Services

import com.GaspillageZeroAPI.DAO.ÉvaluationDAO
import com.GaspillageZeroAPI.Modèle.Livraison
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Modèle.Évaluation
import org.springframework.stereotype.Service

@Service
class ÉvaluationService(val service: ÉvaluationDAO) {


    fun obtenirÉvaluations(): List<Évaluation> {
        return service.chercherTous()
    }

    fun chercherParCodeÉvaluation(idÉvaluation: Int): Évaluation? = service.chercherParCodeÉvaluation(idÉvaluation)









}