package com.example.skipthefast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class FormActivity : AppCompatActivity() {

    lateinit var foodChainSpinner: Spinner
    lateinit var categorySpinner: Spinner
    lateinit var itemSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        foodChainSpinner = findViewById(R.id.foodchain)
        categorySpinner = findViewById(R.id.category)
        itemSpinner = findViewById(R.id.item)

        val foodChains = arrayOf("MC","BK","WEN","SUB")
        val categories =  arrayOf("meat","fish","burger","sandwich")
        val items =  arrayOf("bigMac","Whopper","Bacon","Club")


        foodChainSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, foodChains)
        categorySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories)
        itemSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items)

        foodChainSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                Log.i("Form", foodChains[position])
            }
        }
        categorySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                Log.i("Form", categories[position])
            }
        }
        itemSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                Log.i("Form", items[position])
            }
        }
    }
}
