package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DueDateUtilsTest {
    @Test
    fun dueDateLabel_activeWithoutDate_returnsEmpty() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = null

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("", actual)
    }

    @Test
    fun dueDateLabel_completedWithoutDate_returnsClear() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = null

        // WHEN
        val actual = dueDateLabel(dueDate, today, true)

        // THEN
        assertEquals("CLEAR", actual)
    }

    @Test
    fun dueDateLabel_completedPastDate_returnsClear() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-10-16")

        // WHEN
        val actual = dueDateLabel(dueDate, today, true)

        // THEN
        assertEquals("CLEAR", actual)
    }

    @Test
    fun dueDateLabel_completedToday_returnsClear() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-10-17")

        // WHEN
        val actual = dueDateLabel(dueDate, today, true)

        // THEN
        assertEquals("CLEAR", actual)
    }

    @Test
    fun dueDateLabel_completedFutureDate_returnsClear() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-10-18")

        // WHEN
        val actual = dueDateLabel(dueDate, today, true)

        // THEN
        assertEquals("CLEAR", actual)
    }

    @Test
    fun dueDateLabel_yesterday_returnsDPlus1() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-10-16")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D+1", actual)
    }

    @Test
    fun dueDateLabel_today_returnsDDay() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-10-17")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-Day", actual)
    }

    @Test
    fun dueDateLabel_tomorrow_returnsDMinus1() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-10-18")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-1", actual)
    }

    @Test
    fun dueDateLabel_beforeOneMonth_returnsDMinus30() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-11-16")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-30", actual)
    }

    @Test
    fun dueDateLabel_exactlyOneMonth_returnsDMinus31() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-11-17")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-31", actual)
    }

    @Test
    fun dueDateLabel_afterOneMonth_returnsDMinus32() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-10-17")
        val dueDate = LocalDate.parse("2026-11-18")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-32", actual)
    }

    @Test
    fun dueDateLabel_beforePastMonth_returnsDPlus32() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-11-17")
        val dueDate = LocalDate.parse("2026-10-16")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D+32", actual)
    }

    @Test
    fun dueDateLabel_exactlyPastMonth_returnsDPlus31() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-11-17")
        val dueDate = LocalDate.parse("2026-10-17")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D+31", actual)
    }

    @Test
    fun dueDateLabel_afterPastMonth_returnsDPlus30() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-11-17")
        val dueDate = LocalDate.parse("2026-10-18")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D+30", actual)
    }

    @Test
    fun dueDateLabel_beforeYearEnd_returnsDMinus364() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-01-01")
        val dueDate = LocalDate.parse("2026-12-31")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-364", actual)
    }

    @Test
    fun dueDateLabel_exactlyOneYear_returnsDMinus365() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-01-01")
        val dueDate = LocalDate.parse("2027-01-01")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-365", actual)
    }

    @Test
    fun dueDateLabel_afterOneYear_returnsDMinus366() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2026-01-01")
        val dueDate = LocalDate.parse("2027-01-02")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-366", actual)
    }

    @Test
    fun dueDateLabel_pastYear_returnsDPlus365() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2027-01-01")
        val dueDate = LocalDate.parse("2026-01-01")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D+365", actual)
    }

    @Test
    fun dueDateLabel_beforeLeapDay_returnsDDay() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2024-02-28")
        val dueDate = LocalDate.parse("2024-02-28")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-Day", actual)
    }

    @Test
    fun dueDateLabel_onLeapDay_returnsDMinus1() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2024-02-28")
        val dueDate = LocalDate.parse("2024-02-29")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-1", actual)
    }

    @Test
    fun dueDateLabel_afterLeapDay_returnsDMinus2() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2024-02-28")
        val dueDate = LocalDate.parse("2024-03-01")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-2", actual)
    }

    @Test
    fun dueDateLabel_pastLeapDay_returnsDPlus2() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2024-03-01")
        val dueDate = LocalDate.parse("2024-02-28")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D+2", actual)
    }

    @Test
    fun dueDateLabel_nonLeapMarch_returnsDMinus1() {
        // GIVEN - Explicit calendar dates; the expected label comes from the feature contract.
        val today = LocalDate.parse("2025-02-28")
        val dueDate = LocalDate.parse("2025-03-01")

        // WHEN
        val actual = dueDateLabel(dueDate, today, false)

        // THEN
        assertEquals("D-1", actual)
    }

}
