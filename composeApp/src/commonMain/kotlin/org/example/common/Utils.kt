package org.example.common

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.benasher44.uuid.uuid4
import kotlinx.datetime.*

data class MonthDay(val month: Int, val day: Int)


fun generateUUID(): String {
    val uuid = uuid4().toString()
    return uuid.toString()

}


fun getCurrentSalutation(): String {
    // Get the current time in UTC
    val currentTime = Clock.System.now()
    // Convert to the system's current time zone
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = currentTime.toLocalDateTime(timeZone)

    val hour = localDateTime.hour
    val date = localDateTime.date

    // Define known holidays
    val holidays = mapOf(
        MonthDay(1, 1) to "Happy New Year!",
        MonthDay(12, 25) to "Merry Christmas!",
        MonthDay(7, 4) to "Happy Independence Day!",
        MonthDay(5, 1) to "Happy Labor Day!",
        MonthDay(10, 1) to "Happy International Day of Older Persons!"
    )

    // Check for holiday greetings
    val monthDay = MonthDay(date.monthNumber, date.dayOfMonth)
    holidays[monthDay]?.let { return it }

    // Default time-based greetings
    return when (hour) {
        in 0..11 -> "Good morning!"
        in 12..16 -> "Good afternoon!"
        in 17..20 -> "Good evening!"
        else -> "Good night!"
    }
}

fun painterWithBackground(
    basePainter: Painter,
    backgroundColor: Color,
    cornerRadius: Dp = 0.dp
): Painter {
    return object : Painter() {
        override val intrinsicSize = basePainter.intrinsicSize

        override fun DrawScope.onDraw() {
            // Draw the background
            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
            )
            // Draw the original painter
            with(basePainter) {
                draw(size = size)
            }
        }
    }
}


