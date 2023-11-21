package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Exceptions.ProduitIntrouvableException
import com.GaspillageZeroAPI.Exceptions.ÉpicerieIntrouvableException
import com.GaspillageZeroAPI.Modèle.Produit
import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ProduitService
import org.springframework.web.bind.annotation.*


@RestController
class ProduitControleur(val service: ProduitService) {

    @GetMapping("/produits")
    fun obtenirProduits() = service.chercherTous()

    @GetMapping("/produit/{idProduit}")
    fun obtenirProduitParCode(@PathVariable idProduit: Int): Produit?{
        val produit = service.chercherParCode(idProduit)
        if(produit == null){
            throw ProduitIntrouvableException("Le produit de code $idProduit est introuvable")
        }
        return produit
    }

    @GetMapping("/épicerie/{idÉpicerie}/produits")
    fun obtenirProduitÉpicerie(@PathVariable idÉpicerie: Int): List<Produit> {
        val épicerie = service.chercherParÉpicerie(idÉpicerie)
        //Toujours null épicerie
        if(épicerie == null){
            throw ÉpicerieIntrouvableException("L'épicerie de code $idÉpicerie est introuvable")
        }
        return épicerie
    }


//    @GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
//    fun obtenirProduitÉpicerieParCode(@PathVariable idÉpicerie: Int, @PathVariable idProduit: Int)
//    val produit = service.chercherParÉpicerieParCode(idÉpicerie, idProduit)

    @PostMapping("/produit")
    fun ajouterProduit(@RequestBody produit: Produit) {
        service.ajouter(produit)
    }
    @DeleteMapping("/produit/delete/{idProduit}")
    fun suppimerProduit(@PathVariable idProduit: Int) {
        val produit = service.supprimer(idProduit)
        if(produit == null){
            throw ProduitIntrouvableException("Le produit de code $idProduit est introuvable")
        }
        service.supprimer(idProduit)
    }
    @PutMapping("/produit/save/{idProduit}")
    fun modifierProduit(@PathVariable idProduit: Int, @RequestBody produit: Produit) {
        service.modifier(idProduit, produit)
    }
}