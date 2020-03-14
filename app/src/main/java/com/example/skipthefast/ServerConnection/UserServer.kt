package com.example.skipthefast.ServerConnection

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class UserServer:ProxyServer {
    private var idToken = ""
    private var userId = ""
    private var email = ""
    private var password = ""

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(email:String, password:String): super(){
        this.email = email
        this.password = password
        this.authenticate()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun authenticate(){
        // , callback: (res: JSONObject)->Unit
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
                    Log.w("UserServer", "Failed to authenticate")
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        val responseData = response.body()?.string()
                        val bodyJSON = JSONObject(responseData)
                        idToken = bodyJSON.getString("idToken")
                        userId = bodyJSON.getString("userId")
                    }
                    else{
                        Log.w("UserServer", "Was not able to login")
                    }
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataByDate(year:Number, month:Number, day:Number){
        if(email == "" || password == ""){
            throw Exception("Unspecified user")
        }
    }
}