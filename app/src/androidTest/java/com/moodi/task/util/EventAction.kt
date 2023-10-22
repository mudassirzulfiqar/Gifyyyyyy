package com.moodi.task.util

import android.view.View
import android.widget.EditText
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf

object EventAction {
    fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return null!!
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController?, view: View) {
                val v = view.findViewById<View>(id) as GiphyImageView
                v.performClick()
            }
        }
    }

    fun clearFocus(): ViewAction = object : ViewAction {
        override fun getDescription(): String = "clear focus"

        override fun getConstraints(): Matcher<View> =
            AllOf.allOf(
                ViewMatchers.isDisplayed(),
                ViewMatchers.isAssignableFrom(EditText::class.java)
            )

        override fun perform(uiController: UiController?, view: View?) {
            view?.clearFocus()
        }
    }
}
