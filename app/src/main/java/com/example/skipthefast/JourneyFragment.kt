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
        /*val testData = UserSurvey()
        testData.chain = "MK"
        testData.emotion = "Happy"
        testData.exercise = "Running"
        userInputs.add(testData)
        userInputs.add(testData)
        userInputs.add(testData)
        userInputs.add(testData)
        userInputs.add(testData)

        for (userInput in userInputs) run {
            println("Creating Card!")
            val newCard = Card(context!!, userInput)
            cardsLayout.addView(newCard.getCard())
        }*/
    }

    override fun populateCard(userInput: UserSurvey) {
        cardsLayout.invalidate()
        println("Creating Card!")
        val newCard = Card(context!!, userInput)
        cardsLayout.setOnClickListener(View.OnClickListener {
            val dialoguePopup:DialoguePopup = DialoguePopup(newCard.getUserData().emotion, newCard.getUserData().exercise)
            dialoguePopup.show(activity!!.supportFragmentManager, "WADADADDAA")

        })
        cardsLayout.addView(newCard.getCard())
    }
}
