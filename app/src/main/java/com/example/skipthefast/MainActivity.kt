package com.example.skipthefast

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.viewpager.widget.ViewPager
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.ServerConnection.UserServer

import com.example.skipthefast.ui.main.MainPagerAdapter

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import java.util.*

class MainActivity : AppCompatActivity() {
    private val LAUNCH_FORM_ACTIVITY: Int = 1

    interface ListenFromActivity {
        fun populateCard(userInput: UserSurvey, updateDB: Boolean = true)
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

        fab.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivityForResult(intent, LAUNCH_FORM_ACTIVITY);
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

                if(UserServer.isAuthenticated) {
                    UserServer.pushData(userInput, fun(res) {
                        Looper.prepare()
                        if(res.isSuccessful) {
                            CalendarFragment.updateCalendar(findViewById(R.id.compactcalendar_view))
                            val alertDialog = AlertDialog.Builder(this)
                            alertDialog.setTitle("Push User Data")
                            alertDialog.setMessage("Succesfully Pushed")
                            alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
                                // Lead them back to login page

                            }
                            alertDialog.show()

                            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            val intent = Intent(this, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

                            val builder = NotificationCompat.Builder(this, "4")
                                .setSmallIcon(R.drawable.ic_android_black)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                // Set the intent that will fire when the user taps the notification
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)

                            with(NotificationManagerCompat.from(this)) {
                                // notificationId is a unique int for each notification that you must define
                                notify(4, builder.build())
                            }
                            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                        } else {
                            val alertDialog = AlertDialog.Builder(this)
                            alertDialog.setTitle("ERROR")
                            alertDialog.setMessage("Error: Uncessfull in pushing user data")
                            alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
                                // Lead them back to login page
                            }
                            alertDialog.show()
                        }
                        Looper.loop()
                    })
                }
            }
        }



    }


}