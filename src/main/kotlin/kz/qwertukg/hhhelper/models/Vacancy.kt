package kz.qwertukg.hhhelper.models

import java.net.URLDecoder
import javax.persistence.*

@Entity
@Table(name = "vacancies")
class Vacancy(
        var reply: Boolean = false,
        var replied: Boolean = false,
        var keyword: String = "",
        var url: String = "",
        var result: Int = 0,
        var position: String = "",
        var salary: Int? = null,
        var company: String = "",
        var location: String = "",
        var description: String = "",

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int = 0
) {
    val positionLink: String get() = "<a href='$url' target='_blank' title='$description'>$position</a>"

    val replyCheckbox: String get() {
        if (replied) {
            return "<input type='checkbox' checked disabled>"
        }

        if (reply ) {
            return "<input data-id='$id' class='reply-checkbox' type='checkbox' checked>"
        }

        return "<input data-id='$id' class='reply-checkbox' type='checkbox'>"
    }

    @Suppress("DEPRECATION")
    val keywordDecoded: String get() = URLDecoder.decode(keyword)
}