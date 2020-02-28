package com.example.skipthefast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_exercise.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        runBtn.setOnClickListener{ view ->
            Log.d("happy button", "clicked")
        }
        walkBtn.setOnClickListener{ view ->
            Log.d("glad button", "clicked")
        }
        bikeBtn.setOnClickListener{ view ->
            Log.d("meh button", "clicked")
        }
        breatheBtn.setOnClickListener{ view ->
            Log.d("sad button", "clicked")
        }
    }
}
