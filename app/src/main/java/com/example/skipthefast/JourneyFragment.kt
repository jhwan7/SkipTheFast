package com.example.skipthefast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.com.Card
import kotlinx.android.synthetic.main.fragment_journey.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val testData1 = UserSurvey()
        testData1.chain = "MC"
        testData1.emotion = "happy"
        testData1.exercise = "run"
        userInputs.add(testData1)

        val testData2 = UserSurvey()
        testData2.chain = "BK"
        testData2.emotion = "sad"
        testData2.exercise = "walk"
        userInputs.add(testData2)

        val testData3 = UserSurvey()
        testData3.chain = "BK"
        testData3.emotion = "meh"
        testData3.exercise = "breathe"
        userInputs.add(testData3)

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
            val dialoguePopup = DialoguePopup(userInput.emotion, userInput.exercise)
            dialoguePopup.show(activity!!.supportFragmentManager, "WADADADDAA")
        }
        return newCard
    }
}
