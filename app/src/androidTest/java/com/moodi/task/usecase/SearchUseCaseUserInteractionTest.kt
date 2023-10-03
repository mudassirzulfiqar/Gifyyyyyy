package com.moodi.task.usecase

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moodi.task.R
import com.moodi.task.adapter.SearchAdapter
import com.moodi.task.screen.activity.HomeActivity
import com.moodi.task.screen.fragment.RandomFragment
import com.moodi.task.screen.fragment.SearchFragment
import com.moodi.task.util.EventAction
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchUseCaseUserInteractionTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verify_user_search_results_and_on_clicking_it_opens_detail_screen() {

        onView(withId(R.id.search_edit_textview)).perform(typeText("picnic"))

        /**
         * Adding this delay to wait for the search results to populate
         * Mock Webserver can be used to remove this delay or Fake Repository can be used
         */
        Thread.sleep(2000)

        activityRule.scenario.onActivity {
            it.supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is SearchFragment) {
                    assert(fragment.isVisible)
                }
                if (fragment is RandomFragment) {
                    assert(!fragment.isVisible)
                }

            }
        }
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<SearchAdapter.ViewHolder>(
                0,
                EventAction.clickChildViewWithId(R.id.gif_image_layout)
            )
        )

        // verify the detail activity is visible
        onView(withId(R.id.title_textview)).check(matches(isDisplayed()))
    }




}