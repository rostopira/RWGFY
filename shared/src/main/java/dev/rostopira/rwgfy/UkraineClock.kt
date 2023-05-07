package dev.rostopira.rwgfy

import java.time.*

object UkraineClock {
    private val zone = ZoneId.of("Europe/Kiev")
    private val clock = Clock.system(zone)

    private val today: LocalDate get() = LocalDate.now(clock)
    private val tomorrow: LocalDate get() = LocalDate.now(clock).plusDays(1)

    private fun LocalDate.isToday(): Boolean {
        val now = today
        return now.dayOfMonth == dayOfMonth &&
               now.monthValue == monthValue &&
               now.year == year
    }

    private fun offset(): ZoneOffset {
        val instant = Instant.now(clock)
        return zone.rules.getOffset(instant)
    }

    fun nextUpdateTime(lastUpdate: LocalDate): Long =
        (if (lastUpdate.isToday()) tomorrow else today)
            .atTime(8, 0)
            .toInstant(offset())
            .toEpochMilli()
}