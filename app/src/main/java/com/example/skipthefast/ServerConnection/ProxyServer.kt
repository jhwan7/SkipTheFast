package com.example.skipthefast.ServerConnection

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

open class ProxyServer {
    protected val SERVER_URL = "http://99.79.79.245:8000"

    @RequiresApi(Build.VERSION_CODES.O)
    fun testConnection(){
        val client = OkHttpClient()
        val testURL = "$SERVER_URL/test"
        val request = Request.Builder()
            .url(testURL)
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.w("ProxyServer", "Failed Connection $testURL")
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                Log.w("ProxyServer", "Successful Connection")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createUser(email:String, password:String){
        // , callback: (res: JSONObject)->Unit
        val client = OkHttpClient()
        val createUserURL = "$SERVER_URL/user"
        val bodyBuilder = FormBody.Builder()

        bodyBuilder.add("email", email)
        bodyBuilder.add("password", password)
        val putBody = bodyBuilder.build()

        val request = Request.Builder()
            .url(createUserURL)
            .put(putBody)
            .build()

        val call = client.newCall(request)

        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.w("ProxyServer", "Failed to create new account")
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                Log.w("ProxyServer", "Successfully created new account")
            }
        })
    }
}

