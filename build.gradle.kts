plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("io.micronaut.application") version "4.5.4"
    id("com.gradleup.shadow") version "8.3.7"
    id("io.micronaut.aot") version "4.5.4"
}

version = "0.1"
group = "com.example"

val kotlinVersion = project.properties.get("kotlinVersion")


dependencies {
    ksp("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-http-client")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured")
    testImplementation("net.javacrumbs.json-unit:json-unit:4.1.1")
    implementation("com.github.micronaut-projects:micronaut-serialization:2.15.x-SNAPSHOT")
}


application {
    mainClass = "com.example.ApplicationKt"
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}


graalvmNative.toolchainDetection = false

micronaut {
    version("4.9.0")
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}
