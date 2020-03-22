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
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.skipthefast.ServerConnection.UserServer


var testUserName = "Joe7@uwo.ca"
var testPassword = "1234"

class LoginActivity : AppCompatActivity() {

    lateinit var loginBtn:Button

    lateinit var email: EditText
    lateinit var password:EditText
    lateinit var signupBtn:Button
    lateinit var checkBox: CheckBox

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.login_btn)
        email = findViewById(R.id.email_login)
        password = findViewById(R.id.password_login)
        signupBtn = findViewById(R.id.signup_btn)


        loginBtn.setOnClickListener{

            // Retrieved the email, password
            val loginEmail = email.text.toString()
            val loginPassword = password.text.toString()

//          Edit this function to reflect backend authentication
            UserServer.authenticate(loginEmail, loginPassword, fun(res) {
                Looper.prepare()
                if(res.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Incorrect Information")
                    alertDialog.setMessage("Your username or password is incorrect")
                    alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
                        // Lead them back to login page
                    }
                    alertDialog.show()
                }
                Looper.loop()
            })

        }

        signupBtn.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}

