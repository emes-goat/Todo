package org.emes.todo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendlyDateFormatterTest {

    private val fixedInstant = Instant.parse("2025-01-01T00:00:00.000Z")
    private val formatter = FriendlyDateFormatter(Clock.fixed(fixedInstant, ZoneId.of("UTC")))

    @ParameterizedTest
    @MethodSource("dateProvider")
    fun testFormatDate(input: LocalDate, expected: String) {
        val actual = formatter.format(input)
        assertEquals(expected, actual)
    }

    private fun dateProvider(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(LocalDate.of(2024, 12, 31), "Overdue"),
            Arguments.of(LocalDate.of(2025, 1, 1), "1 Jan - Today"),
            Arguments.of(LocalDate.of(2025, 1, 2), "2 Jan - Tomorrow"),
            Arguments.of(LocalDate.of(2025, 1, 3), "3 Jan - Friday"),
            Arguments.of(LocalDate.of(2025, 1, 7), "7 Jan - Tuesday"),
            Arguments.of(LocalDate.of(2025, 1, 8), "8 Jan"),
            Arguments.of(LocalDate.of(2025, 12, 31), "31 Dec"),
            Arguments.of(LocalDate.of(2026, 1, 1), "1 Jan 2026")
        )
    }
}