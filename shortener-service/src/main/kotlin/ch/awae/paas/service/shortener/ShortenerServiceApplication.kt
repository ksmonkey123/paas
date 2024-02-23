package ch.awae.paas.service.shortener

import ch.awae.paas.audit.*
import ch.awae.paas.auth.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.context.annotation.*


@SpringBootApplication
@Import(ClientSecurityConfiguration::class, AuditLogConfiguration::class)
class ShortenerServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(ShortenerServiceApplication::class.java, *args)
}
