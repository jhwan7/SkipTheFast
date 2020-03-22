package com.example.skipthefast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.skipthefast.Data.SharedViewModel

class Notify: AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.skipthefast"
    private val description = "test"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedViewModel = SharedViewModel.getInstance()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableVibration(true)

            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_android_black)
                .setContentTitle("SkipTheFast")
                .setStyle(Notification.BigTextStyle().bigText("Your goal was ${sharedViewModel.getFrequencyGoal()} and you have reached ${sharedViewModel.getFrequencyUser()}\n" +
                        "You have spent total ${sharedViewModel.getCostUser()} you have ${sharedViewModel.getCostGoal() -  sharedViewModel.getCostUser()} left to spend"))
                .setContentText("Your goal was ${sharedViewModel.getFrequencyGoal()} and you have reached ${sharedViewModel.getFrequencyUser()}\n" +
                        "You have spent total ${sharedViewModel.getCostUser()} you have ${sharedViewModel.getCostGoal().minus(sharedViewModel.getCostUser())} left to spend")
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_android_black)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
        }

        notificationManager.notify(1234, builder.build());

        val nextState = Intent(this, MainActivity::class.java)
        startActivityForResult(nextState, 1);
    }


}