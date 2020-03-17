package com.example.skipthefast

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.skipthefast.Data.UserSurvey
import com.github.sundeepk.compactcalendarview.domain.Event
import java.time.LocalDate
import java.time.Year
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {

    //    Test data variable
    private var test: UserSurvey = UserSurvey();

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
            set(2020, 3 - 1, 2, 8, 45)
            timeInMillis
        }
        val eventDate2: Long = Calendar.getInstance().run {
            set(2020, 3 - 1, 4, 8, 45)
            timeInMillis
        }
        val eventDate3: Long = Calendar.getInstance().run {
            set(2020, 3 - 1, 5, 8, 45)
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
            override fun onDayClick(dateClicked: Date) {

                //Dummy data
                test.chain = "MC"
                test.category = "burgers"
                test.emotion = "Sad"
                test.exercise = "Walk"
                test.price = 5.95f
                test.item = "Big Mac"
                test.date = dateClicked

                val events = compactcalendar_view.getEvents(dateClicked)
                if (events.size > 0) {
//                    Toast.makeText(context, "${events[0].data}", Toast.LENGTH_SHORT).show()
                    val dialoguePopup = DialoguePopup(test)
                    dialoguePopup.show(activity!!.supportFragmentManager, "WADADADDAA")
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                val time = firstDayOfNewMonth.toString().split(" ")
                monthYear.text = time[1] + "  " + time[5]
            }
        })

    }
}
