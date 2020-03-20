package com.example.skipthefast.ServerConnection

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.skipthefast.Data.UserSurvey
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
/* USAGE EXAMPLE */
//UserServer.authenticate("jsong336@uwo.ca", "test1234", fun() {
//    UserServer.pushData("FAT FOOD", 3, "MC", "BIG MAC")
//    UserServer.pushData("FAT FOOD2", 3, "MC", "BIG MAC")
//    UserServer.pushData("FAT FOOD3", 3, "MC", "BIG MAC")
//})
//
//if(UserServer.isAuthenticated){
//    UserServer.getDataByDate(2020, 3, 14, fun(data:JSONObject){
//        Log.w("UserServer", data.toString())
//    })
//}
//else{
//    UserServer.authenticate("jsong336@uwo.ca", "test1234", fun() {
//        UserServer.getDataByDate(2020, 3, 14, fun(data:JSONObject){
//            for(key in data.keys()){
//                Log.w("FBReturn", data.get(key).toString())
//            }
//        })
//    })
//}
object UserServer: ProxyServer() {
    private var idToken = ""
    private var userId = ""
    var isAuthenticated = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun authenticate(email:String, password:String, callback:(Response)->Unit={}){
        if(email == "" || password == ""){
            throw Exception("Unspecified user")
        }
        else{
            val client = OkHttpClient()
            val createUserURL = "$SERVER_URL/login"
            val bodyBuilder = FormBody.Builder()

            bodyBuilder.add("email", email)
            bodyBuilder.add("password", password)
            val postBody = bodyBuilder.build()

            val request = Request.Builder()
                .url(createUserURL)
                .post(postBody)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // TODO("Display no connection message to users")
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        val responseData = response.body()?.string()
                        val bodyJSON = JSONObject(responseData)
                        idToken = bodyJSON.getString("idToken")
                        userId = bodyJSON.getString("userId")
                        isAuthenticated = true
                    }
                    else{
                        Log.w("US_Auth", "Was not able to login")
                    }
                    callback(response)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataByDate(year:Number, month:Number, day:Number, callback:(Response)->(Unit)){
        if(!isAuthenticated){
            throw Exception("Unauthenticated user")
        }
        else{
            val client = OkHttpClient()
            val createUserURL = "$SERVER_URL/data"
            val bodyBuilder = FormBody.Builder()

            bodyBuilder.add("year", year.toString())
            bodyBuilder.add("month", month.toString())
            bodyBuilder.add("day", day.toString())
            bodyBuilder.add("userId", userId)
            bodyBuilder.add("idToken", idToken)

            val postBody = bodyBuilder.build()

            val request = Request.Builder()
                .url(createUserURL)
                .post(postBody)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // TODO("Display no connection message to users")
                }
                override fun onResponse(call: Call, response: Response) {
                    callback(response)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(callback:(Response)->(Unit)){
        if(!isAuthenticated){
            throw Exception("Unauthenticated user")
        }
        else{
            val client = OkHttpClient()
            val createUserURL = "$SERVER_URL/data"
            val bodyBuilder = FormBody.Builder()

            bodyBuilder.add("userId", userId)
            bodyBuilder.add("idToken", idToken)

            val postBody = bodyBuilder.build()

            val request = Request.Builder()
                .url(createUserURL)
                .post(postBody)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // TODO("Display no connection message to users")
                }
                override fun onResponse(call: Call, response: Response) {
                    callback(response)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun pushData(newData:UserSurvey, callback:(Response)->Unit){
        if(!isAuthenticated){
            throw Exception("Unauthenticated user")
        }
        else{
            val client = OkHttpClient()
            val createUserURL = "$SERVER_URL/data"
            val bodyBuilder = FormBody.Builder()

            bodyBuilder.add("Category", newData.category)
            bodyBuilder.add("Feeling", newData.emotion)
            bodyBuilder.add("FoodChain", newData.chain)
            bodyBuilder.add("Item", newData.item)
            bodyBuilder.add("Exercise", newData.exercise)
            bodyBuilder.add("Price", newData.price.toString())
            bodyBuilder.add("userId", userId)
            bodyBuilder.add("idToken", idToken)

            val putBody = bodyBuilder.build()

            val request = Request.Builder()
                .url(createUserURL)
                .put(putBody)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // TODO("Display no connection message to users")
                }
                override fun onResponse(call: Call, response: Response) {
                    callback(response)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun pushGoal(goal:String, callback:(Response)->Unit){
        if(!isAuthenticated){
            throw Exception("Unauthenticated user")
        }
        else{
            val client = OkHttpClient()
            val createUserURL = "$SERVER_URL/goal"
            val bodyBuilder = FormBody.Builder()

            bodyBuilder.add("userId", userId)
            bodyBuilder.add("idToken", idToken)
            bodyBuilder.add("Goal", goal)

            val putBody = bodyBuilder.build()
            val request = Request.Builder()
                .url(createUserURL)
                .put(putBody)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // TODO("Display no connection message to users")
                }
                override fun onResponse(call: Call, response: Response) {
                    callback(response)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getGoal(callback:(Response)->Unit){
        if(!isAuthenticated){
            throw Exception("Unauthenticated user")
        }
        else{
            val client = OkHttpClient()
            val createUserURL = "$SERVER_URL/goal"
            val bodyBuilder = FormBody.Builder()

            bodyBuilder.add("userId", userId)
            bodyBuilder.add("idToken", idToken)

            val postBody = bodyBuilder.build()
            val request = Request.Builder()
                .url(createUserURL)
                .put(postBody)
                .build()

            val call = client.newCall(request)

            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // TODO("Display no connection message to users")
                }
                override fun onResponse(call: Call, response: Response) {
                    callback(response)
                }
            })
        }
    }
}