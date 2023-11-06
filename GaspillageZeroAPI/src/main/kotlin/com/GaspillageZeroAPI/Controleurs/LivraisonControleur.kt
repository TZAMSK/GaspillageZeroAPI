package com.GaspillageZeroAPI.Controleurs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

class LivraisonControleur {


    @GetMapping("/listeLivraisons")
    fun obtenirListeLivraisons() {

    }


    @GetMapping("/listeLivraisons/{code}")
    fun obtenirGabaritProduitParCode(@PathVariable code: String)  {

    }


    /*@PostMapping("/listeLivraisons")
    fun AjouterLivraison(@RequestBody livraison :Livraison) {

    }*/

    @GetMapping("/épicerie/{idÉpicerie}/produit/{idProduit}")
    fun obtenirCommandeÉpicParCode(@PathVariable idCommande : Int, @PathVariable idProduit: Int) {}


}