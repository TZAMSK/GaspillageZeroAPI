package com.GaspillageZeroAPI.Controleurs

import com.GaspillageZeroAPI.Modèle.Épicerie
import com.GaspillageZeroAPI.Services.ÉpicerieService
import org.springframework.web.bind.annotation.*

@RestController
class ÉpicerieControleur(val service: ÉpicerieService) {

    @GetMapping("/épiceries")
    fun obtenirÉpiceries() = service.chercherTous()

    @GetMapping("/épicerie/{idÉpicerie}")
    fun obtenirÉpicerieparCode(@PathVariable idÉpicerie: Int) = service.chercherParCode(idÉpicerie)

    @PostMapping("/épicerie")
    fun ajouterÉpicerie(@RequestBody épicerie: Épicerie) {
        service.ajouter(épicerie)
    }

    @DeleteMapping("/épicerie/delete/{idÉpicerie}")
    fun suppimerÉpicerie(@PathVariable idÉpicerie: Int) {
        service.suppimer(idÉpicerie)
    }

    @PutMapping("/épicerie/save")
    fun modifierÉpicerie(@PathVariable idÉpicerie: Int, @RequestBody épicerie: Épicerie){
        service.modifier(idÉpicerie, épicerie)
    }
}