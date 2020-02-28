package com.example.skipthefast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_emotion.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmotionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmotionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emotion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        happyBtn.setOnClickListener{ view ->
            Log.d("happy button", "clicked")
        }
        gladBtn.setOnClickListener{ view ->
            Log.d("glad button", "clicked")
        }
        mehBtn.setOnClickListener{ view ->
            Log.d("meh button", "clicked")
        }
        sadBtn.setOnClickListener{ view ->
            Log.d("sad button", "clicked")
        }
        miserableBtn.setOnClickListener{ view ->
            Log.d("miserable button", "clicked")
        }
    }
}
