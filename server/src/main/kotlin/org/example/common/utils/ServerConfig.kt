package org.example.common.utils

import com.typesafe.config.ConfigFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory


object ServerConfig {
    private val load = ConfigFactory.load()
    val logger: Logger =  LoggerFactory.getLogger(load.getString("ktor.server.logger") ?: "")
    val apiVersion = load.getString("ktor.server.apiVersion") ?: ""
}