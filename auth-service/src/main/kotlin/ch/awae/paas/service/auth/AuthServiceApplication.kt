package ch.awae.paas.service.auth

import ch.awae.paas.audit.*
import ch.awae.paas.auth.*
import org.springframework.boot.*
import org.springframework.boot.autoconfigure.*
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.*

@SpringBootApplication
@EnableTransactionManagement
@Import(ServiceSecurityConfiguration::class, AuditLogConfiguration::class)
class AuthServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(AuthServiceApplication::class.java, *args)
}
