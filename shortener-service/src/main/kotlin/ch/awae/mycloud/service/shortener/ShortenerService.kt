package ch.awae.mycloud.service.shortener

import ch.awae.mycloud.audit.*
import ch.awae.mycloud.auth.*
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
