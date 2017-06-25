package kz.qwertukg.hhhelper.controllers

import kz.qwertukg.hhhelper.crawlers.*
import kz.qwertukg.hhhelper.models.Profession
import kz.qwertukg.hhhelper.models.User
import kz.qwertukg.hhhelper.repositories.*
import kz.qwertukg.hhhelper.selenium.chromeDriver
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CrawlerController(val repoV: VacancyRepository, val repoP: ProfessionRepository, val repoU: UserRepository) {
    @GetMapping("/")
    fun index(model: Model): String {
        val user = repoU.findAll().firstOrNull() ?: User()
        val action: String
        if (user.id == 0) action = "/users" else action = "/users/${user.id}"
        model.addAttribute("user", user)
        model.addAttribute("action", action)
        return "index"
    }

    // ---

    @PostMapping("/users")
    fun postCreateUser(@ModelAttribute user: User): String {
        repoU.save(user)
        return "redirect:/"
    }

    @PostMapping("/users/{id}")
    fun postUpdateUser(@PathVariable id: Int, @ModelAttribute user: User): String {
        user.id = id
        repoU.save(user)
        return "redirect:/"
    }

    // ---

    @GetMapping("/professions-list")
    fun professionsList(model: Model): String {
        model.addAttribute("action", "/professions")
        model.addAttribute("profession", Profession())
        return "professions-list"
    }

    @PostMapping("/professions")
    fun postCreate(@ModelAttribute profession: Profession): String {
        repoP.save(profession)
        return "redirect:/professions-list"
    }

    @GetMapping("/professions/{id}")
    fun getUpdate(@PathVariable id: Int, model: Model): String {
        model.addAttribute("action", "/professions/$id")
        model.addAttribute("profession", repoP.findOne(id))
        return "professions-list"
    }

    @PostMapping("/professions/{id}")
    fun postUpdate(@PathVariable id: Int, @ModelAttribute profession: Profession): String {
        profession.id = id
        repoP.save(profession)
        return "redirect:/professions-list"
    }

    @GetMapping("/professions/delete/{id}")
    fun getDelete(@PathVariable id: Int): String {
        repoP.delete(id)
        return "redirect:/professions-list"
    }

    //---

    @GetMapping("/find")
    fun find(): String {
        val user = repoU.findAll().firstOrNull() ?: User()

        chromeDriver {
            auth(user.login, user.password)
            find(repoV, repoP)
        }

        return "redirect:/"
    }

    @GetMapping("/reply")
    fun reply(): String {
        val user = repoU.findAll().firstOrNull() ?: User()

        chromeDriver {
            auth(user.login, user.password)
            reply(repoV, repoP)
        }

        return "redirect:/"
    }
}