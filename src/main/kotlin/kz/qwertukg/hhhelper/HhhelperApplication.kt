package kz.qwertukg.hhhelper

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource

@SpringBootApplication
@ImportResource("classpath:beans.xml")
class HhhelperApplication {
    init {
        System.setProperty("webdriver.chrome.driver", "d:/chromedriver.exe")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(HhhelperApplication::class.java, *args)
}
