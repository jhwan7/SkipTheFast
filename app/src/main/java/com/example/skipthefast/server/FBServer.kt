package com.example.skipthefast.server

import okhttp3.*
import java.io.IOException

class FBServer {
    private val client = OkHttpClient()

    fun connect(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })
    }
}