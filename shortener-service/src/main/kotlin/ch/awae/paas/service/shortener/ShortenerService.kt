package ch.awae.paas.service.shortener

import ch.awae.paas.audit.*
import ch.awae.paas.auth.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.context.annotation.*
import org.springframework.transaction.annotation.*

@SpringBootApplication
@EnableTransactionManagement
@Import(ClientSecurityConfiguration::class, AuditLogConfiguration::class)
class ShortenerService

fun main(args: Array<String>) {
    SpringApplication.run(ShortenerService::class.java, *args)
}
