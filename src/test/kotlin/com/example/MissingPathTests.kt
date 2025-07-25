package com.example

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.hasKey
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Test

@MicronautTest
class MissingPathTests {

    @Test
    fun `duplicate stringA error missing path`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": "value", "stringA": "value2" }""")
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(400)
            .body("message", equalTo("Failed to convert argument [body] for value [null] due to: Unknown property [stringA] encountered during deserialization of type: SamplePayload body"))
            .body("$", not(hasKey("path")))
    }

    @Test
    fun `duplicate identical stringA error missing path`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": "value", "stringA": "value", "stringB": "value" }""")
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(400)
            .body("message", equalTo("Failed to convert argument [body] for value [null] due to: Unknown property [stringA] encountered during deserialization of type: SamplePayload body"))
            .body("$", not(hasKey("path")))
    }

    @Test
    fun `duplicate null stringA error missing path`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": null, "stringA": null, "stringB": null }""")
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(400)
            .body("message", equalTo("Failed to convert argument [body] for value [null] due to: Unknown property [stringA] encountered during deserialization of type: SamplePayload body"))
            .body("$", not(hasKey("path")))
    }

    @Test
    fun `unexpected property at start error missing path`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "unknownProperty": "value3", "stringA": "value", "stringB": "value2" }""")
            .post("/invalid-payload")
            .then()
            .statusCode(400)
            .header("Content-Type", "application/json")
            .body("$", not(hasKey("path")))
            .body("message", equalTo("Failed to convert argument [body] for value [null] due to: Unknown property [unknownProperty] encountered during deserialization of type: SamplePayload body"))
    }

    @Test
    fun `unexpected property at end error missing path`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": "value", "unknownProperty": "value3" }""")
            .post("/invalid-payload")
            .then()
            .statusCode(400)
            .header("Content-Type", "application/json")
            .body("$", not(hasKey("path")))
            .body("message", equalTo("Failed to convert argument [body] for value [null] due to: Unknown property [unknownProperty] encountered during deserialization of type: SamplePayload body"))
    }

    @Test
    fun `unexpected property with missing expected error missing path`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "unknownProperty": "value3", "stringA": "value" }""")
            .post("/invalid-payload")
            .then()
            .statusCode(400)
            .header("Content-Type", "application/json")
            .body("$", not(hasKey("path")))
            .body("message", equalTo("Failed to convert argument [body] for value [null] due to: Unknown property [unknownProperty] encountered during deserialization of type: SamplePayload body"))
    }
}
