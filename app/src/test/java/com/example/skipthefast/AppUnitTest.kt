package com.example.skipthefast

import android.content.Context
import android.util.Log
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

    @Test
    fun CalendarDateConversion( ) {
        val jsonDateResponse = JSONObject("{\"2020-03-20 03:48:18\":{\"Category\":\"meat\",\"Exercise\":\"walk\",\"Feeling\":\"2\",\"Food Chain\":\"MC\",\"Item\":\"bigMac\",\"Price\":2},\"2020-03-21 03:48:58\":{\"Category\":\"meat\",\"Exercise\":\"run\",\"Feeling\":\"5\",\"Food Chain\":\"MC\",\"Item\":\"bigMac\",\"Price\":6.45}}")
        val keys = jsonDateResponse.names()

        val expectedDate1 = "2020-03-20"
        val expectedDate2 = "2020-03-21"

        val data = mutableListOf<String>()
        for (i in 0 until jsonDateResponse.length()) {
            Log.i("Calendar", keys.get(i).toString())
            data.add(keys.get(i).toString().split(" ")[0])

        }
        Assert.assertEquals(expectedDate1, data[0])
        Assert.assertEquals(expectedDate2, data[1])

    }

}
