package com.example.skipthefast

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.skipthefast.server.FBServer
import com.example.skipthefast.ui.main.SectionsPagerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    lateinit var ref :  DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        var fbServer = FBServer();
        fab.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        ref = FirebaseDatabase.getInstance().getReference("records/mathieu")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError){
                TODO("not implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                   for(h in p0.children){
                       println(h.value)
                   }
                }
            }
        })
    }
}