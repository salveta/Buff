package com.buffup.app

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BuffViewTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<FullscreenActivity>(FullscreenActivity::class.java, true, false)

    @Before
    fun setup() {
        FullscreenActivity.layout = R.layout.buff_container
    }

    @Test
    fun isBuffViewShowInScreen() {
        restartActivity()
        onView(withId(R.id.buffView)).check(matches(isDisplayed()))
    }

    @Test
    fun areElementsHideUntilGetData() {
        restartActivity()
        onView(withId(R.id.incSender)).check(matches(not(isDisplayed())))
        onView(withId(R.id.incQuestion)).check(matches(not(isDisplayed())))
        onView(withId(R.id.incAnswer)).check(matches(not(isDisplayed())))
    }

    private fun restartActivity() {
        if (activityRule.activity != null) {
            activityRule.finishActivity()
        }
        activityRule.launchActivity(Intent())
    }
}