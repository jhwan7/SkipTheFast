package com.example.skipthefast

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.server.FBServer
import com.example.skipthefast.ui.main.MainPagerAdapter

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity() {
    private val LAUNCH_FORM_ACTIVITY: Int = 1

    interface ListenFromActivity {
        fun populateCard(userInput: UserSurvey)
    }

    lateinit var mainListener: ListenFromActivity

    fun setActivityListener(mainListener: ListenFromActivity) {
        this.mainListener = mainListener;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        var fbServer = FBServer();
        fab.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivityForResult(intent, LAUNCH_FORM_ACTIVITY);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LAUNCH_FORM_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                val userInput = UserSurvey()
                userInput.chain = data?.getStringExtra("chain").toString()
                userInput.emotion = data?.getStringExtra("emotion").toString()
                userInput.exercise = data?.getStringExtra("exercise").toString()
                userInput.category = data?.getStringExtra("category").toString()
                userInput.price = data?.getFloatExtra("price", 0f)!!
                userInput.item = data?.getStringExtra("item").toString()
                userInput.date = Date(System.currentTimeMillis())

                mainListener.populateCard(userInput)
            }
        }



    }


}