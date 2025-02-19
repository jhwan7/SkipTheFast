package com.example.skipthefast

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.skipthefast.ServerConnection.ProxyServer

class SignupActivity : AppCompatActivity() {

    lateinit var name_text: EditText
    lateinit var username_text: EditText
    lateinit var email_text: EditText
    lateinit var confirm_email_text: EditText
    lateinit var password_text: EditText
    lateinit var signup_btn: Button

    fun displayFeedBack(message:String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Create User")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
            // Lead them back to login page
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        name_text = findViewById(R.id.name_signup)
        username_text = findViewById(R.id.username_signup)
        email_text = findViewById(R.id.email_signup)
        confirm_email_text = findViewById(R.id.confirm_email_signup)
        password_text = findViewById(R.id.password_signup)
        signup_btn = findViewById(R.id.signup_btn_2)

        signup_btn.setOnClickListener{
            // Send this data to the backend, store user
            val name = name_text.text.toString()
            val username = username_text.text.toString()
            val email = email_text.text.toString()
            val confirm_email = confirm_email_text.text.toString()
            val password = password_text.text.toString()

            if (email == confirm_email) {
                val proxy_server = ProxyServer()
                proxy_server.createUser(email, password, fun(response){
                    Looper.prepare()
                    if(response.isSuccessful){
                        displayFeedBack("Successful!")
                    }
                    else{
                        displayFeedBack("Could not create new account: Please try different email or password")
                    }
                    Looper.loop()
                })
            }
            else{
                displayFeedBack("Please check email and confirmed email")
            }
        }
    }
}