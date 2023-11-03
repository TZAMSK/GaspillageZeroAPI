package com.GaspillageZeroAPI.DAO

import com.GaspillageZeroAPI.Modèle.Épicerie

interface ÉpicerieDAO: DAO<Épicerie> {

    override fun chercherTous(): List<Épicerie>
    override fun chercherParCode(idÉpicerie: Int): Épicerie?
    override fun ajouter(épicerie: Épicerie): Épicerie?
    override fun supprimer(idÉpicerie: Int): Épicerie?
    override fun modifier(idÉpicerie: Int, épicerie: Épicerie): Épicerie?
}