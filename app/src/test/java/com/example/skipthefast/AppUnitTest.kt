package com.example.skipthefast

import android.content.Context
import com.example.skipthefast.Data.UserSurvey
import org.apache.commons.lang3.builder.EqualsBuilder
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class AppUnitTest {

    @Mock
    private lateinit var mockTestContext: Context;

    /**
     * UserSurvey Object is crucial to the application functionality as it's an object used to store
     * user survey information that gets shared between activites and fragments.
     */
    @Test
    fun `JSON object to UserSurvey object conversion test`() {
        val jsonUserSurvey = JSONObject("{\"Food Chain\":\"test\",\"Feeling\":\"test\",\"Price\":1.1,\"Exercise\":\"test\",\"Category\":\"test\",\"Item\":\"test\"}")

        val userSurveyToTest: UserSurvey
        val expectedUserSurvey: UserSurvey

        // Creating test object
        val timeNow = Date()
        userSurveyToTest = UserSurvey(jsonUserSurvey, timeNow)

        // Creating desired test object
        expectedUserSurvey = UserSurvey()
        expectedUserSurvey.chain = "test"
        expectedUserSurvey.emotion = "test"
        expectedUserSurvey.price = 1.1f
        expectedUserSurvey.exercise = "test"
        expectedUserSurvey.category = "test"
        expectedUserSurvey.item = "test"
        expectedUserSurvey.date = timeNow

        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedUserSurvey, userSurveyToTest));
    }

}
