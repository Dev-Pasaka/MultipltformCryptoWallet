package org.example.common.utils

fun generateShortcode(): String {
    val charset = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..6)
        .map { charset.random() }
        .joinToString("")
}