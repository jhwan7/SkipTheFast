package com.example.skipthefast.ServerConnection

import android.os.Build
import androidx.annotation.RequiresApi

class UserServer:ProxyServer {
    private var idToken = ""
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

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataByDate(year:Number, month:Number, day:Number){

    }
}