package com.moodi.task.screen.activity

import android.content.Intent
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moodi.task.R
import com.moodi.task.data.local.GiphyAppModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    @Test
    fun verify_detail_activity_views() {

        launch<DetailActivity>(getIntent())
        onView(withId(R.id.back_image_button)).check(matches(isDisplayed()))
        onView(withId(R.id.title_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.giphy_image_view)).check(matches(isDisplayed()))
        onView(withId(R.id.giphy_link_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.giphy_sub_title_textview)).check(matches(isDisplayed()))
        onView(withId(R.id.giphy_rating_textview)).check(matches(isDisplayed()))

    }

    @Test
    fun verify_data_in_views() {
        launch<DetailActivity>(getIntent())

        onView(withId(R.id.title_textview)).check(matches(withText(API_DATA_SEARCH.title)))
        onView(withId(R.id.giphy_link_textview)).check(matches(withText(API_DATA_SEARCH.displayLink)))
        onView(withId(R.id.giphy_sub_title_textview)).check(matches(withText(API_DATA_SEARCH.title)))
        onView(withId(R.id.giphy_rating_textview)).check(matches(withText(API_DATA_SEARCH.ageRate)))

    }


    private fun getIntent(): Intent {
        val intentData =
            Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java)
        intentData.putExtra(DetailActivity.GIPHY_URL, API_DATA_SEARCH.animatedUrl)
        intentData.putExtra(DetailActivity.TITLE, API_DATA_SEARCH.title)
        intentData.putExtra(DetailActivity.RATING, API_DATA_SEARCH.ageRate)
        intentData.putExtra(DetailActivity.IMAGE_SHORT_URL, API_DATA_SEARCH.displayLink)

        return intentData
    }


    companion object {
        private val API_DATA_SEARCH = GiphyAppModel(
            id = "jUwpNzg9IcyrK",
            ageRate = "g",
            title = "Scared Homer Simpson GIF by reactionseditor",
            animatedUrl = "https://media0.giphy.com/media/jUwpNzg9IcyrK/giphy.gif?cid=6df7b0fe6y3krnvtibak73aqyt9x0wi7nc7jyj4b0j54lj6u&ep=v1_gifs_search&rid=giphy.gif&ct=g",
            displayLink = "http://gph.is/Vx9dyv",
            stillUrl = "https://media0.giphy.com/media/jUwpNzg9IcyrK/giphy_s.gif?cid=6df7b0fe6y3krnvtibak73aqyt9x0wi7nc7jyj4b0j54lj6u&ep=v1_gifs_search&rid=giphy_s.gif&ct=g"
        )

    }

}