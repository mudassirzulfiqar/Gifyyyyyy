package com.moodi.task.screen.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moodi.task.R
import com.moodi.task.util.EventAction.clearFocus
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HomeActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // launch HomeActivity
    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    //  test to verity behaviour of search custom layout
    @Test
    fun verify_user_interaction_with_search_layout() {

        // verify if search layout is displayed
        onView(withId(R.id.search_toolbar_view)).check(matches(isDisplayed()))

        // verify if the back button is not displayed when the user is not focused on it
        onView(withId(R.id.back_image_button)).check(matches(not(isDisplayed())))

        // verify if the clear button is not displayed when the user is not focused on it
        onView(withId(R.id.clear_image_button)).check(matches(not(isDisplayed())))

        // verify hint
        onView(withId(R.id.search_edit_textview)).check(matches(withHint(R.string.search_view_hint)))
    }

    @Test
    fun verify_only_back_button_visibility_when_user_clicks() {

        // clear focus
        onView(withId(R.id.search_edit_textview)).perform(clearFocus())

        // click on text view
        onView(withId(R.id.search_edit_textview)).perform(click())
        // verify if back button is displayed when the user focus on it
        onView(withId(R.id.back_image_button)).check(matches(isDisplayed()))
        // should not show clear button
        onView(withId(R.id.clear_image_button)).check(matches(not(isDisplayed())))
    }

    @Test
    fun verify_visibility_of_clear_button_back_button_when_user_search() {

        // clear focus
        onView(withId(R.id.search_edit_textview)).perform(clearFocus())

        // type 2 characters
        onView(withId(R.id.search_edit_textview)).perform(typeText("hi"))
        // verify if back button is displayed when the user starts writing
        onView(withId(R.id.back_image_button)).check(matches(isDisplayed()))
        // should show clear button
        onView(withId(R.id.clear_image_button)).check(matches(isDisplayed()))
    }

    @Test
    fun verify_visibility_of_clear_button_back_button_when_user_type_text_and_click_clear_button() {
        // clear focus
        onView(withId(R.id.search_edit_textview)).perform(clearFocus())

        // type 2 characters
        onView(withId(R.id.search_edit_textview)).perform(typeText("hi"))
        // clear text
        onView(withId(R.id.clear_image_button)).perform(click())
        // verify text is cleared
        onView(withId(R.id.search_edit_textview)).check(matches(withText("")))
        // should not show clear button
        onView(withId(R.id.clear_image_button)).check(matches(not(isDisplayed())))

    }

    @Test
    fun verify_empty_state_search_view_when_user_search_and_press_back_button() {

        // clear focus
        onView(withId(R.id.search_edit_textview)).perform(clearFocus())

        // type 2 characters
        onView(withId(R.id.search_edit_textview)).perform(typeText("hi"))
        // clear text
        onView(withId(R.id.back_image_button)).perform(click())
        // verify text is cleared
        onView(withId(R.id.search_edit_textview)).check(matches(withText("")))
        // should not show clear button
        onView(withId(R.id.clear_image_button)).check(matches(not(isDisplayed())))
        // should not show back button
        onView(withId(R.id.back_image_button)).check(matches(not(isDisplayed())))
    }

    @Test
    fun verify_on_search_search_fragment_should_visible() {

        // clear focus
        onView(withId(R.id.search_edit_textview)).perform(clearFocus())

        onView(withId(R.id.search_edit_textview)).perform(typeText("picnic"))

        activityRule.scenario.onActivity {
            it.supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is SearchFragment) {
                    assert(fragment.isVisible)
                }
            }
        }
    }

    /**
     * For triangulation purpose we are checking the random fragment is not visible
     */
    @Test
    fun verify_on_search_random_should_not_visible() {

        // clear focus
        onView(withId(R.id.search_edit_textview)).perform(clearFocus())

        onView(withId(R.id.search_edit_textview)).perform(typeText("picnic"))

        activityRule.scenario.onActivity {
            it.supportFragmentManager.fragments.forEach { fragment ->
                if (fragment is RandomFragment) {
                    assert(!fragment.isVisible)
                }
            }
        }
    }


}