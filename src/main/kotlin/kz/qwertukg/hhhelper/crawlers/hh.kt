package kz.qwertukg.hhhelper.crawlers

import kz.qwertukg.hhhelper.models.*
import kz.qwertukg.hhhelper.repositories.ProfessionRepository
import kz.qwertukg.hhhelper.repositories.VacancyRepository
import kz.qwertukg.hhhelper.selenium.*

import org.openqa.selenium.WebDriver

fun WebDriver.auth(login: String, pass: String) {
    get("https://hh.kz/login")

    elementByName("username") { sendKeys(login) }

    elementByName("password") {
        sendKeys(pass)
        submit()
    }
}

fun WebDriver.find(repoV: VacancyRepository, repoP: ProfessionRepository) {
    val vacancies = mutableListOf<Vacancy>()
    val oldVacancies = repoV.findAll()

    repoP.findAll().forEach { profession ->
        get("https://hh.kz/search/vacancy?text=${profession.keyword}&items_on_page=200&only_with_salary=false&search_field=name&items_on_page=100&area=160&area=159&enable_snippets=true&clusters=true&no_magic=true&salary=")

        elementsByClass("search-result-item").forEach {
            val url = it.elementByClass("search-result-item__name").getAttribute("href").removeQueryParams()
            val label = it.elementByClass("search-result-item__label")
            val name = it.elementByClass("search-result-item__name").text
            val salary = it.elementByClassOrNull("b-vacancy-list-salary")?.text?.average()
            val company = it.elementByClass("search-result-item__company").text
            val location = it.elementByClass("searchresult__address").text
            val description = it.elementBySelector("div[data-qa='vacancy-serp__vacancy_snippet_requirement']").text

            if (!label.isDisplayed) {
                if (!vacancies.any { it.url == url } && !oldVacancies.any { it.url == url }) {
                    vacancies.add(Vacancy(false, false, profession.keyword, url, 0, name, salary, company, location, description))
                }
            } else {
                var result = 0
                when (label.text) {
                    "Вы приглашены!" -> result = 1
                    "Вам отказали" -> result = -1
                }

                if (oldVacancies.any { it.url == url }) {
                    val v = oldVacancies.first{ it.url == url }
                    v.result = result
                    vacancies.add(v)
                } else if (!vacancies.any { it.url == url }) {
                    vacancies.add(Vacancy(true, true, profession.keyword, url, result, name, salary, company, location, description))
                }
            }
        }
    }

    if (vacancies.any()) {
        repoV.save(vacancies)
    }
}

fun WebDriver.reply(repoV: VacancyRepository, repoP: ProfessionRepository) {
    val vacancies = repoV.findAll()
    val professions = repoP.findAll()

    vacancies.filter { it.reply && !it.replied }.forEach {
        get("https://hh.kz/applicant/vacancy_response?vacancyId=${it.url.id()}")

        elementByClassOrNull("bloko-link-switch") { click() }

        waitElementByClass("HH-VacancyResponsePopup-Letter", 10) {
            sendKeys(professions.first { p -> p.keyword == it.keyword }.message)
            submit()

            it.replied = true
        }

        if (!existsElementByClass("m-attention_good")) { it.result = -1000 }

        repoV.save(it)
    }
}

fun String.average(): Int? {
    val arr = split("-")
    val first: String
    val second: String

    if (arr.size == 1) {
        first = arr[0].replace(Regex("\\D+"), "")
        return first.toInt()
    }

    if (arr.size == 2) {
        first = arr[0].replace(Regex("\\D+"), "")
        second = arr[1].replace(Regex("\\D+"), "")
        return (first.toInt() + second.toInt()) / 2
    }

    return null
}

fun String.removeQueryParams(): String {
    val arr = split("?")
    return arr[0]
}

fun String.id(): String {
    val arr = split("/")
    return arr[arr.size - 1]
}