package com.locuslabs.crserc

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun shouldIncrementTwiceWithoutDelay() {
        onView(withId(R.id.incrementByTwoButton)).perform(click())

        onView(withId(R.id.counterTextView))
            .check(matches(withText("[init(0), by1(0), by2(2), by1(2), by2(4)]")))
    }

    @Test
    fun shouldIncrementTwiceWithDelay() {
        onView(withId(R.id.addDelay)).perform(click())

        onView(withId(R.id.incrementByTwoButton)).perform(click())

        onView(withId(R.id.counterTextView))
            .check(matches(withText("[init(0), by1(0), by2(2), by1(2), by2(4)]")))
    }
}
