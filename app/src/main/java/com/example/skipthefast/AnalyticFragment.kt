package com.example.skipthefast

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.skipthefast.ServerConnection.UserServer
import kotlinx.android.synthetic.main.fragment_analytic.*

class AnalyticFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analytic, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Thread(Runnable {
            UserServer.getGraph('r', fun(res) {
                if (res.isSuccessful) {
                    val responseData = res.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(responseData)
                    activity!!.runOnUiThread(fun() {
                        user_freq.setImageBitmap(bitmap)
                    })
                } else {
                    Log.w("US_Push", "Pushing data unsuccessful")
                }
            })
            UserServer.getGraph('m', fun(res) {
                if (res.isSuccessful) {
                    val responseData = res.body()?.byteStream()
                    val bitmap = BitmapFactory.decodeStream(responseData)
                    activity!!.runOnUiThread(fun() {
                        user_expense.setImageBitmap(bitmap)
                    })
                } else {
                    Log.w("US_Push", "Pushing data unsuccessful")
                }
            })
        }).start()
    }
}
