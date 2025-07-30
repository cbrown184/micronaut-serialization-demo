package com.example

import io.micronaut.context.annotation.Primary
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.serde.exceptions.SerdeException
import jakarta.inject.Singleton

@Controller("/")
class SampleController {

    @Post("/invalid-payload") fun payloadError(@Body body: SamplePayload) = "hello"

    @Serdeable
    data class SamplePayload(
        val stringA: String?,
        val stringB: String?,
        val nestedObject: NestedPayload?
    )

    @Serdeable
    data class NestedPayload(
        val nestedString: String?
    )
}

@Singleton
@Primary
class ErrorHandler: ExceptionHandler<ConversionErrorException, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>, exception: ConversionErrorException): HttpResponse<*> {
        return HttpResponse.badRequest(InvalidParametersResponsePayload(
            message = exception.message,
            path = (exception.cause as? SerdeException)?.path?.map { it.argument.name }
        ))
    }
}

@Serdeable
data class InvalidParametersResponsePayload(
    val message: String?,
    val path: List<String>?
)

