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
import kotlinx.android.synthetic.main.fragment_emotion.*
import java.lang.RuntimeException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionFragment(formActivity: Context) : Fragment() {

    private var model: Communicator?=null
    private var formActivity: FormActivity = formActivity as FormActivity

    private var userInput: UserSurvey = UserSurvey()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emotion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model= ViewModelProviders.of(activity!!).get(Communicator::class.java)

        happyBtn.setOnClickListener{ view ->
            model!!.setEmotionCommunicator("happy")
            userInput.emotion = "happy"
            formActivity.nextTab()
        }
        gladBtn.setOnClickListener{ view ->
            model!!.setEmotionCommunicator("glad")
            userInput.emotion = "glad"
            formActivity.nextTab()
        }
        mehBtn.setOnClickListener{ view ->
            model!!.setEmotionCommunicator("meh")
            userInput.emotion = "meh"
            formActivity.nextTab()
        }
        sadBtn.setOnClickListener{ view ->
            model!!.setEmotionCommunicator("sad")
            userInput.emotion = "sad"
            formActivity.nextTab()
        }
        miserableBtn.setOnClickListener{ view ->
            model!!.setEmotionCommunicator("miserable")
            userInput.emotion = "miserable"
            formActivity.nextTab()
        }
    }
}
