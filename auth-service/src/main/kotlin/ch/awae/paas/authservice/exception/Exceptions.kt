package ch.awae.paas.authservice.exception

import org.springframework.http.*
import org.springframework.web.server.*

class BadLoginException :
    ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid login credentials")

class ResourceAlreadyExistsException(resource: String) :
    ResponseStatusException(HttpStatus.BAD_REQUEST, "resource already exists: $resource")

class ResourceNotFoundException(resource : String) :
    ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found: $resource")
