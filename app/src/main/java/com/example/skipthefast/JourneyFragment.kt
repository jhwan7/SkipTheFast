package com.example.skipthefast

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.com.Card
import com.example.skipthefast.server.FBServer
import kotlinx.android.synthetic.main.fragment_journey.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [JourneyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JourneyFragment : Fragment(), MainActivity.ListenFromActivity {
    val userInputs: MutableList<UserSurvey> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setActivityListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journey, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testData1 = UserSurvey()
        testData1.chain = "MC"
        testData1.emotion = "happy"
        testData1.exercise = "run"
        testData1.category = "burger"
        testData1.price = 10.5f
        testData1.date = Date(System.currentTimeMillis() - 86400000 * 4)
        userInputs.add(testData1)

        val testData2 = UserSurvey()
        testData2.chain = "BK"
        testData2.emotion = "sad"
        testData2.exercise = "walk"
        testData2.category = "meat"
        testData2.price = 8.5f
        testData2.date = Date(System.currentTimeMillis() - 86400000 * 2)
        userInputs.add(testData2)

        val testData3 = UserSurvey()
        testData3.chain = "BK"
        testData3.emotion = "meh"
        testData3.exercise = "breathe"
        testData3.category = "sandwich"
        testData3.price = 13.5f
        testData3.date = Date(System.currentTimeMillis() - 86400000 * 2)
        userInputs.add(testData3)

        val testData4 = UserSurvey()
        testData4.chain = "BK"
        testData4.emotion = "happy"
        testData4.exercise = "bike"
        testData4.category = "sandwich"
        testData4.price = 11.5f
        testData4.date = Date(System.currentTimeMillis() - 86400000 * 1)
        userInputs.add(testData4)

        val testData5 = UserSurvey()
        testData5.chain = "BK"
        testData5.emotion = "meh"
        testData5.exercise = "run"
        testData5.category = "sandwich"
        testData5.price = 12.5f
        testData5.date = Date(System.currentTimeMillis() - 86400000 * 1)
        userInputs.add(testData5)

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        date.text = formatter.format(Date(System.currentTimeMillis()))
        totalUserEntry.text = userInputs.size.toString() + " entries total"

        for (userInput in userInputs) run {
            cardsLayout.addView(createCard(userInput))
        }
    }

    override fun populateCard(userInput: UserSurvey) {
        cardsLayout.invalidate()
        userInputs.add(userInput)
        cardsLayout.addView(createCard(userInput))

        totalUserEntry.text = userInputs.size.toString() + " entries total"
    }

    fun createCard(userInput: UserSurvey): View {
        val newCard = Card(context!!, userInput).getCard()
        newCard.setOnClickListener {
            val dialoguePopup = DialoguePopup(userInput)
            dialoguePopup.show(activity!!.supportFragmentManager, "WADADADDAA")
        }
        return newCard
    }
}
