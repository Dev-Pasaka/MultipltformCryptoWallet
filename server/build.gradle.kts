plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
    application
}

group = "org.example.project"
version = "1.0.0"
application {
    mainClass.set("org.example.project.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.kotlin.test.junit)

    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.call.id)
    implementation(libs.ktor.simple.cache)
    implementation(libs.ktor.simple.redis.cache)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.ktor.mockk)
    implementation(libs.mongodb.core)
    implementation(libs.mongodb.serializer)
    implementation(libs.jbcrypt)
    implementation(libs.user.agent)
    implementation(libs.geo.ip)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.kotlin.aws.s3)
    implementation(libs.kotlin.encryption)
    implementation(libs.ktor.redis)
    implementation(libs.ktor.micrometrics)
    implementation(libs.ktor.prometheus)
    implementation(libs.ktor.websockets)
}