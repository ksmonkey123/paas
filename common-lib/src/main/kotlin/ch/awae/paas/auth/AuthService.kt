package ch.awae.paas.auth

interface AuthService {
    fun authenticateToken(tokenString: String): AuthInfo?
}