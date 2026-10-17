package com.example.swpp.tododapp.statistics

import com.example.swpp.tododapp.data.Task
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class StatisticsUtilsTest(
    private val tasks: List<Task>?,
    private val expectedActive: Float,
    private val expectedCompleted: Float
) {
    @Test
    fun getActiveAndCompletedStats_givenTasks_returnsExpectedPercentages() {
        // GIVEN - Inputs and independent percentages are supplied by the table.
        // WHEN
        val result = getActiveAndCompletedStats(tasks)
        // THEN - Float comparisons allow rounding, while catching integer division.
        assertEquals(expectedActive, result.activeTasksPercent, 0.0001f)
        assertEquals(expectedCompleted, result.completedTasksPercent, 0.0001f)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: active={1}%, completed={2}%")
        fun data(): List<Array<Any?>> = listOf(
            arrayOf(listOf(task("1", true), task("2", false)), 50f, 50f),
            arrayOf(emptyList<Task>(), 0f, 0f),
            arrayOf(listOf(task("1", true), task("2", true)), 0f, 100f),
            arrayOf(listOf(task("1", false), task("2", false)), 100f, 0f),
            arrayOf(null, 0f, 0f),
            arrayOf(listOf(task("1", false), task("2", true), task("3", true)),
                33.333332f, 66.666664f)
        )

        private fun task(id: String, completed: Boolean) =
            Task(title = "Task $id", description = "Description $id", isCompleted = completed, id = id)
    }
}
