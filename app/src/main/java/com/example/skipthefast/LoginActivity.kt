package com.example.skipthefast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

var testUserName = "Joe7@uwo.ca"
var testPassword = "1234"

class LoginActivity : AppCompatActivity() {

    lateinit var loginBtn:Button
    lateinit var email: EditText
    lateinit var password:EditText
    lateinit var signupBtn:Button
    lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.login_btn)
        email = findViewById(R.id.email_login)
        password = findViewById(R.id.password_login)
        signupBtn = findViewById(R.id.signup_btn)
        checkBox = findViewById(R.id.checkBox)

        loginBtn.setOnClickListener{
            // Retrieved the email, password
            val loginEmail = email.text.toString()
            val loginPassword = password.text.toString()

//          Do something with check box to allow remember ability
            val remember = checkBox.isChecked

//          Edit this function to reflect backend authentication
            if(loginEmail == testUserName && testPassword == loginPassword) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }

        signupBtn.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}

