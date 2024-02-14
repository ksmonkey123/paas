package ch.awae.paas.service.auth.exception

import org.springframework.http.*
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.*

class BadLoginException :
    ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid login credentials")

class InvalidPasswordException :
    ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password")

class ResourceAlreadyExistsException(resource: String) :
    ResponseStatusException(HttpStatus.BAD_REQUEST, "resource already exists: $resource")

class ResourceNotFoundException(resource: String) :
    ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found: $resource")
