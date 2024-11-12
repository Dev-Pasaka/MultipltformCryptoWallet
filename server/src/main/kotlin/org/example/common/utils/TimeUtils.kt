package org.example.common.utils

import java.time.Instant

object TimeUtils {
    fun getCurrentUtcTimestamp(): Instant {
        return Instant.now()
    }
}