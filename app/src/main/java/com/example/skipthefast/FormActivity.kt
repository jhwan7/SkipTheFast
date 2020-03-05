package com.example.skipthefast

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.skipthefast.Data.UserActivity
import com.example.skipthefast.ui.main.FormsPagerAdapter
import com.google.android.material.tabs.TabLayout

class FormActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var sectionsPagerAdapter: FormsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        sectionsPagerAdapter = FormsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    fun nextTab() {
        if (viewPager.currentItem < sectionsPagerAdapter.count) {
            viewPager.currentItem += 1
        }
    }

    fun closeActivity(userInput: UserActivity) {
        val returnIntent = Intent()
        returnIntent.putExtra("chain", userInput.chain)
        returnIntent.putExtra("emotion", userInput.emotion)
        returnIntent.putExtra("exercise", userInput.exercise)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
