package com.example.skipthefast.server

import okhttp3.*
import java.io.IOException

class FBServer {
    private val client = OkHttpClient()

    fun connect(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()
        println("CONNECTING")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("FAILED")
                println(e)
            }
            override fun onResponse(call: Call, response: Response) {
                println("Successfull"+response.body()?.string())
            }
        })
        println("CONNECTED??")
    }
}