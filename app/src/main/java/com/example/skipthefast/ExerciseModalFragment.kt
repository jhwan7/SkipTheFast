package com.example.skipthefast

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseModalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseModalFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return activity!!.layoutInflater.inflate(R.layout.fragment_exercise_modal, container)
    }
}
