package ch.awae.paas.auth

import org.springframework.boot.context.properties.*

@ConfigurationProperties(prefix = "paas.security")
data class SecurityProperties (val publicEndpoints: List<String>?)