package org.example.common

import com.benasher44.uuid.uuid4


fun generateUUID(): String {
    val uuid = uuid4().toString()
    return uuid.toString()

}