package com.example.skipthefast

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.skipthefast.ServerConnection.ProxyServer

var credentials = 1234

class LoginActivity : AppCompatActivity() {

    lateinit var loginBtn:Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.login)

        loginBtn.setOnClickListener{
            val password = findViewById<EditText>(R.id.password).text.toString().toInt()
            // Retrieved the password
            // Compare this with the backend credential information
            val us = ProxyServer()
            us.testConnection()
            // credentials = Value from REST API, OR SEND Password to verify.
            if(password == credentials) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }
}
