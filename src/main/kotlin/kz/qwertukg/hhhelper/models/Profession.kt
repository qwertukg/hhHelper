package kz.qwertukg.hhhelper.models

import java.net.URLDecoder
import javax.persistence.*

@Entity
@Table(name = "professions")
class Profession(
        var keyword: String = "",
        var message: String = "",

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int = 0
) {
    @Suppress("DEPRECATION")
    val keywordDecoded: String get() = URLDecoder.decode(keyword)

    val actions: String get() = "<div class='text-right'><a href='/professions/$id' class='success'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></a> <a href='/professions/delete/$id'><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></a></div>"
}