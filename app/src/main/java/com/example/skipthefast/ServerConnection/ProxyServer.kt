package com.example.skipthefast.ServerConnection

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import okhttp3.*
import java.io.IOException

open class ProxyServer {
    protected val SERVER_URL = "http://99.79.79.245:8000"

    @RequiresApi(Build.VERSION_CODES.O)
    fun testConnection(){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("$SERVER_URL/test")
            .build()
        val call = client.newCall(request)

        call.enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.w("ProxyServer", "Failed Connection")
            }
            override fun onResponse(call: Call, response: Response) {
                Log.w("ProxyServer", "Successful Connection")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createUser(email:String, password:String){

    }
}

