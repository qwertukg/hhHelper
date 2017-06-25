package kz.qwertukg.hhhelper.models

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        var login: String = "",
        var password: String = "",

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int = 0
)