package com.moodi.task.screen.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import com.moodi.common.API_DATA_SEARCH
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun giphy_is_displayed_on_detail_screen() {
        // Start the app
        val model = API_DATA_SEARCH
        composeTestRule.setContent {
            DetailScreen(
                model,
                onNavUp = { }
            )
        }
        /**
         * Assert that the title is displayed twice on the screen
         */
        composeTestRule.onAllNodesWithText(model.title)[0].assertIsDisplayed()
        composeTestRule.onAllNodesWithText(model.title)[1].assertIsDisplayed()
        composeTestRule.onNodeWithText(model.ageRate).assertIsDisplayed()
        composeTestRule.onNodeWithText("Selected Gif").assertIsDisplayed()
        composeTestRule.onNodeWithText(model.displayLink).assertIsDisplayed()
    }
}
