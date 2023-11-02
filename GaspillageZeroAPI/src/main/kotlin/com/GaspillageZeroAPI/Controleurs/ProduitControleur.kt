package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ProduitService
import org.springframework.web.bind.annotation.*


@RestController
class ProduitControleur(val service: ProduitService) {

    @GetMapping("/produits")
    fun obtenirProduits() = service.chercherTous()

    @GetMapping("/produit/{idProduit}")
    fun obtenirProduitParCode(@PathVariable idProduit: Int) = service.chercherParCode(idProduit)

    @GetMapping("/épicerie/{idÉpicerie}/produits")
    fun obtenirProduitÉpiceruie(@PathVariable idÉpicerie: Int) = service.chercherParÉpicerie(idÉpicerie)

    @GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
    fun obtenirProduitÉpicerieParCode(@PathVariable idÉpicerie: Int, @PathVariable idProduit: Int) = service.chercherParÉpicerieParCode(idÉpicerie, idProduit)

    @PostMapping("/produit")
    fun ajouterProduit(@RequestBody produit: Produit) {
        service.ajouter(produit)
    }
    @DeleteMapping("/produit/delete/{idProduit}")
    fun suppimerProduit(@PathVariable idProduit: Int) {
        service.supprimer(idProduit)
    }
    @PutMapping("/produit/save/{idProduit}")
    fun modifierProduit(@PathVariable idProduit: Int, @RequestBody produit: Produit) {
        service.modifier(idProduit, produit)
    }
}