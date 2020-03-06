package com.example.skipthefast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.example.skipthefast.Message.Communicator
import kotlinx.android.synthetic.main.fragment_select_food.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectFoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectFoodFragment : Fragment() {
    private var model: Communicator?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model= ViewModelProviders.of(activity!!).get(Communicator::class.java)

        val foodChains = arrayOf("MC","BK")
        val categories =  arrayOf("meat","fish","burger","sandwich")
        val items =  arrayOf("bigMac","Whopper","Bacon","Club")


        foodchain.adapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, foodChains) }
        category.adapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, categories) }
        item.adapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, items) }

        foodchain.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                model!!.setChainCommunicator(foodChains[position])
            }
        }
        category.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                model!!.setChainCommunicator(foodChains[position])
            }
        }
        item.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                model!!.setChainCommunicator(foodChains[position])
            }
        }
    }
}
