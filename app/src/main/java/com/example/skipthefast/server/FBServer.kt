package com.example.skipthefast.server

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.*
//import okhttp3.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
//import java.io.IOException

class FBServer {

    //private val client = OkHttpClient()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getRecords(user:String) {
        val recordLister = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val record = dataSnapshot.getValue(Entry::class.java)
                // TODO('RETURN RECORD!!!')
                Log.w( record.toString(),"Returned record froms FB")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        //        ref = FirebaseDatabase.getInstance().getReference("records/mathieu")
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError){
//                TODO("not implemented")
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if(p0!!.exists()){
//                   for(h in p0.children){
//                       println(h.value)
//                   }
//                }
//            }
//        })
        database.addValueEventListener(recordLister)
    }

}