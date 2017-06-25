package kz.qwertukg.hhhelper.controllers

import kz.qwertukg.hhhelper.models.Profession
import kz.qwertukg.hhhelper.repositories.*
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
class ProfessionController(val repo: ProfessionRepository) {
    @GetMapping("/professions")
    fun index() = repo.findAll()



}