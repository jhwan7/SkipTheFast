package com.example.skipthefast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.skipthefast.Data.SharedViewModel
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.ServerConnection.UserServer
import org.json.JSONObject
import java.lang.Exception
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class Notify : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "com.example.skipthefast"
    private val description = "test"

    private var costSum = 0f
    private var freqCount = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = Calendar.getInstance()

        UserServer.getData(fun(res) {

            Looper.prepare()
            if (res.isSuccessful) {
                val data = JSONObject(res.body()!!.string())

                // Get user data first
                data.keys().forEachRemaining { dateStr ->
                    run {
                        val date: Date = Date.from(
                            LocalDateTime.parse(
                                dateStr,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            ).atZone(ZoneId.systemDefault()).toInstant()
                        )
                        val userInput = UserSurvey(data.get(dateStr) as JSONObject, date)

                        calendar.add(Calendar.DAY_OF_YEAR, -7)

                        if (userInput.date > calendar.time) {
                            costSum += userInput.price
                            freqCount++
                        }

                    }
                }
                var sharedViewModel = SharedViewModel.getInstance()
                sharedViewModel.setCostUser(costSum)
                sharedViewModel.setFrequencyUser(freqCount)
                println("User data Retrieved" + sharedViewModel.getCostUser() + sharedViewModel.getFrequencyUser())
                UserServer.getGoal(fun(res) {
                    Looper.prepare()
                    if (res.isSuccessful) {
                        var body = res.body()!!.string()
                        println("Data " + body)
                        // Initialize User Goal Here
                        val data = JSONObject(body)
//                            val goal = data.getJSONObject("Goal")
                        println("Goal Retrieved")
                        println(data)
                        // No goal

                        val goals = data.get("Goal").toString().split("&&&&")
                        println(goals.toString())
                        if (goals[0] == "None") {
                            println("No Response " + goals[0])
                            val nextState = Intent(this, MainActivity::class.java)
                            startActivityForResult(nextState, 1);
                        } else {
                            sharedViewModel.setFrequencyGoal(goals[1].toInt())
                            sharedViewModel.setCostGoal(goals[2].toFloat())
                            // Notification Implementation
                            val sharedViewModel = SharedViewModel.getInstance()
                            notificationManager =
                                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                            val intent = Intent(this, MainActivity::class.java)
                            val pendingIntent = PendingIntent.getActivity(
                                this,
                                0,
                                intent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                            )

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationChannel = NotificationChannel(
                                    channelId,
                                    description,
                                    NotificationManager.IMPORTANCE_HIGH
                                )
                                notificationChannel.enableVibration(true)

                                notificationManager.createNotificationChannel(notificationChannel)
                                builder = Notification.Builder(this, channelId)
                                    .setSmallIcon(R.drawable.main_logo)
                                    .setLargeIcon(
                                        BitmapFactory.decodeResource(
                                            resources,
                                            R.drawable.main_logo
                                        )
                                    )
                                    .setContentTitle("SkipTheFast")
                                    .setStyle(
                                        Notification.BigTextStyle().bigText(
                                            "Your goal was ${sharedViewModel.getFrequencyGoal()} times/week and you have reached ${sharedViewModel.getFrequencyUser()}\n" +
                                                    "You have spent total ${'$'} ${sharedViewModel.getCostUser()} you have ${'$'} ${String.format("%.2f", sharedViewModel.getCostGoal().minus(sharedViewModel.getCostUser())
                                                    )} left to spend"
                                        )
                                    )
                                    .setContentText(
                                        "Your goal was ${sharedViewModel.getFrequencyGoal()} times/week and you have reached ${sharedViewModel.getFrequencyUser()}\n" +
                                                "You have spent total ${'$'}${sharedViewModel.getCostUser()} you have ${'$'}${String.format("%.2f", sharedViewModel.getCostGoal().minus(sharedViewModel.getCostUser())
                                                )} left to spend"
                                    )
                                    // Set the intent that will fire when the user taps the notification
                                    .setContentIntent(pendingIntent)
                            } else {

                                builder = Notification.Builder(this)
                                    .setSmallIcon(R.drawable.main_logo)
                                    .setLargeIcon(
                                        BitmapFactory.decodeResource(
                                            resources,
                                            R.drawable.main_logo
                                        )
                                    )
                                    .setContentTitle("SkipTheFast")
                                    .setStyle(
                                        Notification.BigTextStyle().bigText(
                                            "Your goal was ${sharedViewModel.getFrequencyGoal()} times/week and you have reached ${sharedViewModel.getFrequencyUser()}\n" +
                                                    "You have spent total ${'$'} ${sharedViewModel.getCostUser()} you have ${'$'} ${String.format("%.2f", sharedViewModel.getCostGoal().minus(sharedViewModel.getCostUser())
                                                    )} left to spend"
                                        )
                                    )
                                    .setContentText(
                                        "Your goal was ${sharedViewModel.getFrequencyGoal()} times/week and you have reached ${sharedViewModel.getFrequencyUser()}\n" +
                                                "You have spent total ${'$'}${sharedViewModel.getCostUser()} you have ${'$'}${String.format("%.2f", sharedViewModel.getCostGoal().minus(sharedViewModel.getCostUser())
                                                )} left to spend"
                                    )
                                    // Set the intent that will fire when the user taps the notification
                                    .setContentIntent(pendingIntent)
                            }


                            notificationManager.notify(1234, builder.build());
                            // END
                            println("Returning to Main")
                            // Return to main activity
                            val nextState = Intent(this, MainActivity::class.java)
                            startActivityForResult(nextState, 1);
                        }

                    }
                    Looper.loop()
                })
            }
            Looper.loop()

        })
    }


}