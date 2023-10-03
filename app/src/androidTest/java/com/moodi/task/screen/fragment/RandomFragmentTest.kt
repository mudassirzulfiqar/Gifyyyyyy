package com.moodi.task.screen.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.moodi.task.R
import com.moodi.task.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RandomFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verify_view_visibility_on_random_fragment() {
        launchFragmentInHiltContainer<RandomFragment>(null)
        Espresso.onView(ViewMatchers.withId(R.id.random_label_text_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.giphy_container_constraint_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}