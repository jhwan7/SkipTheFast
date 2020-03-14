package com.example.skipthefast.ServerConnection

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.skipthefast.Data.UserSurvey
import com.google.firebase.database.*
import java.lang.Exception
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/*
* Usage
*
* var fb = FBServer()
* writeEntry(*params) --> push data to FB
*
* fb.getRecords("jeongwon", fun(record){
*   // callback
*   Log.w(record.toString(), "TEST")
* })
*
*  */

class FBServer {
    private val database = FirebaseDatabase.getInstance().reference
    private val URL = "https://test-projec-5a491.firebaseio.com/records"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(): String? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return current.format(formatter)
    }

    @IgnoreExtraProperties
    data class Entry(
        var Date: String? = "",
        var Category: String? = "",
        var Feeling: Int? = 0,
        var FoodChain: String? = "",
        var Item: String? = "",
        var Price: Double? = 0.00
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun writeEntry(user: String, category: String, feeling: Int, foodChain: String, item: String, price: Double){
        // TODO("Add activities")
        if(feeling>5 || feeling >0){
            throw Exception("feeling greater than 5 or less than 0")
        }
        val entry = Entry(getTime(), category, feeling, foodChain, item, price)
        database.child("records").child(user).push().setValue(entry)
    }

    @RequiresApi(Build.VERSION_CODES.O)

    fun getRecords(user:String/*, callback:(JSONObject)->Unit*/) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL+"/"+user+".json")
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    if(response.isSuccessful){
                        var data = JSONObject(response.body()?.string())
                        Log.w(data.toString(), "Recieved data from FB")
                        val userSurveyData = UserSurvey(data)
                        //callback(data)
                    }
                    else{
                        throw Exception("RESPONSE FAIL")
                    }
                }
            }
        })
    }
}