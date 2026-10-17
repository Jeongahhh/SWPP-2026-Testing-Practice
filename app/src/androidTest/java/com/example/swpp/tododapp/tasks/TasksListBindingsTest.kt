package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.swpp.tododapp.data.Task
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {
    private class BindingFixture {
        val today: LocalDate = LocalDate.now()
        val textView = TextView(InstrumentationRegistry.getInstrumentation().targetContext).apply {
            text = "stale label"
            visibility = View.VISIBLE
        }
        fun task(isCompleted: Boolean = false, dueDate: LocalDate? = null) =
            Task("Task", "Description", isCompleted, "task-1", dueDate)
        fun bind(task: Task?) = setDueDateLabel(textView, task)
        fun expect(text: String, visibility: Int) {
            assertEquals("label text", text, textView.text.toString())
            assertEquals("label visibility", visibility, textView.visibility)
        }
    }

    private fun onMainThread(check: BindingFixture.() -> Unit) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            BindingFixture().check()
        }
    }

    @Test
    fun setDueDateLabel_futureDate_showsDMinus1() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(task(dueDate = today.plusDays(1)))
            expect("D-1", View.VISIBLE)
        }
    }

    @Test
    fun setDueDateLabel_today_showsDDay() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(task(dueDate = today))
            expect("D-Day", View.VISIBLE)
        }
    }

    @Test
    fun setDueDateLabel_pastDate_showsDPlus1() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(task(dueDate = today.minusDays(1)))
            expect("D+1", View.VISIBLE)
        }
    }

    @Test
    fun setDueDateLabel_completedWithoutDate_showsClear() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(task(isCompleted = true))
            expect("CLEAR", View.VISIBLE)
        }
    }

    @Test
    fun setDueDateLabel_completedWithDate_showsClear() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(task(isCompleted = true, dueDate = today.plusDays(1)))
            expect("CLEAR", View.VISIBLE)
        }
    }

    @Test
    fun setDueDateLabel_activeWithoutDate_clearsAndHides() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(task())
            expect("", View.GONE)
        }
    }

    @Test
    fun setDueDateLabel_nullTask_clearsAndHides() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(null)
            expect("", View.GONE)
        }
    }

    @Test
    fun setDueDateLabel_completeThenActivateWithoutDate_clearsAndHides() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            val active = task()
            bind(active)
            bind(active.copy(isCompleted = true))
            expect("CLEAR", View.VISIBLE)
            bind(active.copy(isCompleted = false))
            expect("", View.GONE)
        }
    }

    @Test
    fun setDueDateLabel_removeDueDate_clearsAndHides() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            val dated = task(dueDate = today.plusDays(1))
            bind(dated)
            expect("D-1", View.VISIBLE)
            bind(dated.copy(dueDate = null))
            expect("", View.GONE)
        }
    }

    @Test
    fun setDueDateLabel_clearTaskAfterCompleted_clearsAndHides() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(task(isCompleted = true))
            expect("CLEAR", View.VISIBLE)
            bind(null)
            expect("", View.GONE)
        }
    }

    @Test
    fun setDueDateLabel_reuseHiddenViewWithDate_showsLabel() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            bind(null)
            textView.visibility = View.GONE
            bind(task(dueDate = today.plusDays(1)))
            expect("D-1", View.VISIBLE)
        }
    }

    @Test
    fun setDueDateLabel_completeThenActivateWithDate_restoresLabel() {
        // GIVEN - A real TextView with stale content; all bindings use this same instance.
        onMainThread {
            // WHEN / THEN - Assert after each observable transition.
            val dated = task(dueDate = today.plusDays(1))
            bind(dated)
            expect("D-1", View.VISIBLE)
            bind(dated.copy(isCompleted = true))
            expect("CLEAR", View.VISIBLE)
            bind(dated.copy(isCompleted = false))
            expect("D-1", View.VISIBLE)
        }
    }

}
