package com.example

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.hasItem
import org.junit.jupiter.api.Test

@MicronautTest
class NestedFieldTest {
    @Test
    fun `nested duplicata value`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""
                {
                    "stringA": "valueA",
                    "stringB": "second",
                    "nestedObject": {
                        "nestedString": "nestedValue",
                        "nestedString": "nestedValue"
                    }
                }
            """.trimIndent())
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(400)
            .body("path", hasItem("nestedObject.nestedString"))
    }

    @Test
    fun `nested unknown value`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""
                {
                    "stringA": "valueA",
                    "stringB": "second",
                    "nestedObject": {
                        "nestedString": "nestedValue",
                        "unknown": "nestedValue"
                    }
                }
            """.trimIndent())
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(400)
            .body("path", hasItem("nestedObject.unknown"))
    }
}
