package com.example.skipthefast

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.ServerConnection.UserServer
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.json.JSONObject
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    private var header: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)

        val cal = Calendar.getInstance()
        val month = SimpleDateFormat("MMM", Locale.ENGLISH).format(cal.time)
        val time = month.toUpperCase() + " " + Calendar.getInstance().get(Calendar.YEAR).toString()
        view.findViewById<TextView>(R.id.monthYear).setText(time)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compactcalendar_view.setFirstDayOfWeek(Calendar.MONDAY)
        compactcalendar_view.setUseThreeLetterAbbreviation(true)

        // ADD LOOP TO ADD MULTIPLE EVENTS
        val eventDate1: Long = Calendar.getInstance().run {
            set(2020, 3-1, 2, 8, 45)
            timeInMillis
        }
        val eventDate2: Long = Calendar.getInstance().run {
            set(2020, 3-1, 4, 8, 45)
            timeInMillis
        }
        val eventDate3: Long = Calendar.getInstance().run {
            set(2020, 3-1, 5, 8, 45)
            timeInMillis
        }
        val ev1 = Event(Color.GREEN, eventDate1, "Some extra data that I want to store.")
        compactcalendar_view.addEvent(ev1)
        val ev2 = Event(Color.GREEN, eventDate2, "Some extra data that I want to store.")
        compactcalendar_view.addEvent(ev2)
        val ev3 = Event(Color.GREEN, eventDate3, "Some extra data that I want to store.")
        compactcalendar_view.addEvent(ev3)
        // LOOP ENDS HERE

        compactcalendar_view.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDayClick(dateClicked: Date) {
                val events = compactcalendar_view.getEvents(dateClicked)
                if(true) {
                    val df = SimpleDateFormat("MM/dd/yyyy")
                    val now: String = df.format(dateClicked)
                    val date = now.split('/')
                    UserServer.getDataByDate(date[2].toInt(), date[0].toInt(), date[1].toInt(), fun (res) {
                        Looper.prepare()
                        if(res.isSuccessful) {
                            Log.i("Calendar", "WORKED")
                            val userRecord = res.body()?.string()

                            val json = JSONObject(userRecord)
                            Log.i("Calendar", json.length().toString())
                           val keys = json.names()
                            for (i in 0 until json.length()) {
                                //Log.i("Calendar", keys.getString(i))
                                val obj = json.getJSONObject(keys.getString(i))
                                val userData = UserSurvey()
                                userData.chain = obj.getString("Food Chain")
                                userData.category = obj.getString("Category")


                                userData.item = if(obj.has("Item"))  obj.getString("Item") else "n/a"
                                userData.price = if(obj.has("Price"))  obj.getString("Price").toFloat() else -1f
                                userData.emotion = if(obj.has("Feeling"))  obj.getString("Feeling") else "n/a"
                                userData.exercise = if(obj.has("Exercise"))  obj.getString("Exercise") else "n/a"

                                val dialog = DialoguePopup(userData);
                                dialog.show(activity!!.supportFragmentManager, "WADADADDAA");
                                // Your code here
                            }
                        } else {
                            Log.e("Calendar", "Not Working")
                        }
                        Looper.loop()
                    })
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                val time = firstDayOfNewMonth.toString().split(" ")
                monthYear.text = time[1] + "  " + time[5]
            }
        })

    }
}
