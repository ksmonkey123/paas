package ch.awae.paas.audit

import ch.awae.paas.auth.*
import java.sql.*
import java.time.*

class AuditLogEntry() {
    lateinit var timestamp: Timestamp
    lateinit var traceId: String
    lateinit var service: String

    // security information
    var username: String? = null

    // rest request information
    var request: Request? = null

    // method information
    lateinit var method: Method

    constructor(service: String, request: Request?, method: Method) : this() {
        this.timestamp = Timestamp.from(Instant.now())
        this.traceId = TraceInformation.traceId!!
        this.service = service
        this.username = AuthInfo.username
        this.request = request
        this.method = method
    }


    class Request() {
        lateinit var verb: String
        lateinit var path: String

        constructor(verb: String, path: String) : this() {
            this.verb = verb
            this.path = path
        }
    }

    class Method() {
        lateinit var component: String
        lateinit var method: String
        lateinit var parameters: List<Parameter>
        var error: String? = null

        constructor(component: String, method: String, parameters: List<Parameter>, error : String?) : this() {
            this.component = component
            this.method = method
            this.parameters = parameters
            this.error = error
        }

        class Parameter() {
            var position: Int = -1
            var name: String? = null
            var value: String? = null

            constructor(position: Int, name: String?, value: String?) : this() {
                this.position = position
                this.name = name
                this.value = value
            }
        }

    }

}
