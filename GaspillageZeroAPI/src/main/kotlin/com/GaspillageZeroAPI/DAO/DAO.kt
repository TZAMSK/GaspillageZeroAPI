package com.GaspillageZeroAPI.DAO

interface DAO<T> {

    fun chercherTous(): List<T>
    fun chercherParCode(id: Int): T?
    fun ajouter(element: T): T?
    fun supprimer(id: Int): T?
    fun modifier(id: Int, element: T): T?
}