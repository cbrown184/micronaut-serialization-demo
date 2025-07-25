package com.example

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.Test

@MicronautTest
class JsonFieldOrderTests {

    @Test
    fun `unexpected at end returns 200`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": "value", "stringB": "value2", "unknownProperty": "value3" }""")
            .post("/invalid-payload")
            .then()
            .log()
            .all()
            .statusCode(200)
    }

    @Test
    fun `unexpected at beginning returns 400`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "unknownProperty": "value3", "stringA": "value", "stringB": "value2" }""")
            .post("/invalid-payload")
            .then()
            .log()
            .all()
            .statusCode(400)
    }

    @Test
    fun `non-adjacent duplicate stringA at the end returns 200`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": "valuea", "stringB": "valueb", "stringA": "valuea" }""")
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(200)
    }

    @Test
    fun `adjacent duplicate stringB at the end returns 200`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": "valueA", "stringB": "first", "stringB": "second" }""")
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(200)
    }

    @Test
    fun `adjacent duplicate stringA at the start returns 400`(spec: RequestSpecification) {
        spec
            .`when`()
            .header("Content-Type", "application/json")
            .body("""{ "stringA": "valueA", "stringA": "valueA", "stringB": "second" }""")
            .post("/invalid-payload")
            .then()
            .log().all()
            .statusCode(400)
    }

}
