package com.example.skipthefast

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.ServerConnection.UserServer
import com.example.skipthefast.com.Card
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4::class)
class AppInstrumentedTest {

    @get:Rule
    var activityRule = object: ActivityTestRule<MainActivity>(MainActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            UserServer.isTest = true
        }
    }

    lateinit var mainActivity: MainActivity;

    @Before
    fun setup() {
        mainActivity = activityRule.activity;
    }

    /**
     * Test to check if the card got properly created with user survey data input.
     */
    @Test
    fun tabsActivity() {
        val userSurveyData: UserSurvey

        // Creating test object
        val timeNow = Date()

        // Creating desired test object
        userSurveyData = UserSurvey()
        userSurveyData.chain = "test"
        userSurveyData.emotion = "test"
        userSurveyData.price = 1.1f
        userSurveyData.exercise = "test"
        userSurveyData.category = "test"
        userSurveyData.item = "test"
        userSurveyData.date = timeNow

        val newCard: ViewGroup = Card(mainActivity, userSurveyData).getCard()

        assertNotNull(newCard)
    }
}
