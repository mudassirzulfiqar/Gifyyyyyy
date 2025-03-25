package com.moodi.task.screen.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.moodi.task.state.random.RandomState
import com.moodi.task.state.search.SearchState
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun visible_search_view_on_screen() {
        // Start the app

        val searchState = SearchState(
            loading = false,
            data = emptyList(),
            error = ""
        )

        val randomState: RandomState = RandomState.Empty

        composeTestRule.setContent {
            HomeScreen(
                searchState = searchState,
                randomState = randomState,
                onPressedItem = { },
                onSearchClearPress = { },
                onQuerySearch = { }
            )
        }
        /**
         * Assert that the title is displayed twice on the screen
         */
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
        // check the vector image
        composeTestRule.onNodeWithTag("search-icon").assertIsDisplayed()
    }
}
