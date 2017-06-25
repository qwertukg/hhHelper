package kz.qwertukg.hhhelper.repositories

import kz.qwertukg.hhhelper.models.*
import org.springframework.data.repository.CrudRepository

interface VacancyRepository : CrudRepository<Vacancy, Int>