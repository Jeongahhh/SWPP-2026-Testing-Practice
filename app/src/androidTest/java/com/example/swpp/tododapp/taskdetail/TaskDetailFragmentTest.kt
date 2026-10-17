package com.example.swpp.tododapp.taskdetail

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.swpp.tododapp.R
import com.example.swpp.tododapp.ServiceLocator
import com.example.swpp.tododapp.data.Task
import com.example.swpp.tododapp.data.source.FakeAndroidTestRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class TaskDetailFragmentTest {
    private lateinit var repository: FakeAndroidTestRepository

    @Before
    fun initRepository() {
        repository = FakeAndroidTestRepository()
        ServiceLocator.tasksRepository = repository
    }

    @After
    fun cleanupDb() = ServiceLocator.resetRepository()

    @Test
    fun taskDetails_activeTask_displaysTitleDescriptionAndUncheckedBox() = runTest {
        // GIVEN
        val task = Task("Task Title", "Task Description", false, "active-task")
        repository.saveTask(task)
        // WHEN
        val args = TaskDetailFragmentArgs(task.id).toBundle()
        val scenario = launchFragmentInContainer<TaskDetailFragment>(args, R.style.AppTheme)
        try {
            // THEN
            onView(withId(R.id.task_detail_title_text)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_title_text)).check(matches(withText("Task Title")))
            onView(withId(R.id.task_detail_description_text)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_description_text)).check(matches(withText("Task Description")))
            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isDisplayed()))
            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(not(isChecked())))
        } finally {
            scenario.close()
        }
    }

    @Test
    fun taskDetails_completedTask_displaysCheckedBox() = runTest {
        // GIVEN
        val task = Task("Completed Task", "Finished description", true, "completed-task")
        repository.saveTask(task)
        // WHEN
        val scenario = launchFragmentInContainer<TaskDetailFragment>(
            TaskDetailFragmentArgs(task.id).toBundle(), R.style.AppTheme)
        try {
            // THEN
            onView(withId(R.id.task_detail_title_text)).check(matches(withText("Completed Task")))
            onView(withId(R.id.task_detail_description_text)).check(matches(withText("Finished description")))
            onView(withId(R.id.task_detail_complete_checkbox)).check(matches(isChecked()))
        } finally {
            scenario.close()
        }
    }
}
