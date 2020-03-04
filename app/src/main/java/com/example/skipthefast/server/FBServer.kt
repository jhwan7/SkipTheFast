package com.example.skipthefast.server

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
import okhttp3.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.IOException

class FBServer {

    private val client = OkHttpClient()
    private val database = FirebaseDatabase.getInstance().reference

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
        val entry = Entry(getTime(), category, feeling, foodChain, item, price)
        database.child("records").child(user).push().setValue(entry)
    }
}