package ch.awae.paas.service.shortener.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.data.jpa.repository.*
import java.time.*

@Entity
class ShortLink(
    @Id
    @Column(updatable = false)
    val id : String,
    @JsonIgnore
    @Column(updatable = false)
    val username: String,
    @Column(updatable = false)
    val targetUrl: String,
) {
    @Column(updatable = false)
    val creationTime: LocalDateTime = LocalDateTime.now()
}

interface ShortLinkRepository : JpaRepository<ShortLink, String> {
    fun findByUsername(username: String) : List<ShortLink>

    fun findByIdAndUsername(id : String, username : String) : ShortLink?

}