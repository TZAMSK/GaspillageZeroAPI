package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Épicerie

interface ÉpicerieDAO: DAO<Épicerie> {

    override fun chercherTous(): List<Épicerie>
    override fun chercherParCode(id: Int): Épicerie?
    override fun ajouter(épicerie: Épicerie): Épicerie?
    override fun supprimer(id: Int): Épicerie?
    override fun modifier(id: Int, épicerie: Épicerie): Épicerie?
}