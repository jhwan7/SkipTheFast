package com.example.skipthefast.server

import okhttp3.*
import java.io.IOException

class FBServer {
    private val client = OkHttpClient()

    fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                print("FAILED CALL ON ")
                println(url)
                println(e)
            }
            override fun onResponse(call: Call, response: Response) {
                print("Successful call on ")
                println(url)
                println(response.body()?.string())
            }
        })

    }
}