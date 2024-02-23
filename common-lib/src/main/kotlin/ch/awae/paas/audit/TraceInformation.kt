package ch.awae.paas.audit

import org.springframework.http.*
import java.util.*
import kotlin.random.Random

object TraceInformation {

    private val base64Encoder = Base64.getUrlEncoder()
    private val traceIdHolder = ThreadLocal<TraceIdLayer?>()
    private val requestHolder = ThreadLocal<RequestInformation?>()

    val traceId: String?
        get() = traceIdHolder.get()?.toString()

    val requestInfo: RequestInformation?
        get() = requestHolder.get()

    fun init(traceId: String? = null, requestInformation: RequestInformation? = null) {
        traceIdHolder.set(TraceIdLayer(traceId ?: generateId(), null))
        requestHolder.set(requestInformation)
    }

    fun push() {
        traceIdHolder.update { parent -> TraceIdLayer(generateId(), parent) }
    }

    fun pop() {
        traceIdHolder.update { current -> current?.parent }
    }

    private fun <T> ThreadLocal<T>.update(f: (T) -> T) {
        set(f(get()))
    }

    private fun generateId(): String {
        return base64Encoder.encodeToString(Random.nextBytes(9))
    }

    data class RequestInformation(
        val path: String,
        val verb: String,
    )

    private data class TraceIdLayer(
        val id: String,
        val parent: TraceIdLayer?
    ) {
        override fun toString(): String {
            return if (parent != null) {
                "$parent$$id"
            } else {
                id
            }
        }
    }

}