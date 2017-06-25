package kz.qwertukg.hhhelper.controllers

import kz.qwertukg.hhhelper.models.Vacancy
import kz.qwertukg.hhhelper.repositories.*
import org.springframework.web.bind.annotation.*

@RestController
class VacanciesController(val repo: VacancyRepository) {
    @GetMapping("/vacancies")
    fun index() = repo.findAll()

    @GetMapping("/vacancies/vacancy-check/{id}/{value}")
    fun update(@PathVariable id: Int, @PathVariable value: Boolean): Vacancy {
        val model = repo.findOne(id)
        model.reply = value
        repo.save(model)

        return model
    }

}