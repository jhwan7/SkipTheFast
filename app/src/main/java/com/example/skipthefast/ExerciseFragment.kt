package com.example.skipthefast

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.Message.Communicator
import kotlinx.android.synthetic.main.fragment_exercise.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment(formActivity: Context) : Fragment() {
    private var model: Communicator?=null
    private var formActivity: FormActivity = formActivity as FormActivity

    private var userInput: UserSurvey = UserSurvey()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        runBtn.setOnClickListener{ view ->
            Log.d("run button", "clicked")
            userInput.exercise = "run"
            formActivity.closeActivity(userInput)
        }
        walkBtn.setOnClickListener{ view ->
            Log.d("walk button", "clicked")
            userInput.exercise = "walk"
            formActivity.closeActivity(userInput)
        }
        bikeBtn.setOnClickListener{ view ->
            Log.d("bike button", "clicked")
            userInput.exercise = "bike"
            formActivity.closeActivity(userInput)
        }
        breatheBtn.setOnClickListener{ view ->
            Log.d("breathe button", "clicked")
            userInput.exercise = "breathe"
            formActivity.closeActivity(userInput)
        }

        model= ViewModelProviders.of(activity!!).get(Communicator::class.java)

        model!!.chain.observe(this, object: Observer<Any> {
            override fun onChanged(t: Any?) {
                Log.d("exercise fragment: ", "received " + t!!.toString())
                userInput.chain = t.toString()
            }
        })

        model!!.category.observe(this, object: Observer<Any> {
            override fun onChanged(t: Any?) {
                Log.d("exercise fragment: ", "received " + t!!.toString())
                userInput.category = t.toString()
            }
        })
        model!!.item.observe(this, object: Observer<Any> {
            override fun onChanged(t: Any?) {
                Log.d("exercise fragment: ", "received " + t!!.toString())
                userInput.item = t.toString()
            }
        })
        model!!.price.observe(this, object: Observer<Any> {
            override fun onChanged(t: Any?) {
                Log.d("exercise fragment: ", "received " + t!!.toString())
                userInput.price = t.toString().toFloat()
            }
        })
        model!!.emotion.observe(this, object: Observer<Any> {
            override fun onChanged(t: Any?) {
                Log.d("exercise fragment: ", "received " + t!!.toString())
                userInput.emotion = t.toString()
            }
        })
    }
}
