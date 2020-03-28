package com.example.skipthefast

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.skipthefast.ServerConnection.UserServer
import kotlinx.android.synthetic.main.fragment_goal.*
import org.json.JSONObject
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Use the [GoalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goal, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Thread(Runnable {
            UserServer.getGoal(fun(res) {
                try {
                    val data = JSONObject(res.body()!!.string())
                    val text = data.get("Goal").toString()
                    if(text != "None") {
                        val goals = text.split("&&&&")
                        getView()!!.findViewById<EditText>(R.id.costGoalT).setText(goals[2])
                        getView()!!.findViewById<EditText>(R.id.frequencyGoalT).setText(goals[1])
                        getView()!!.findViewById<EditText>(R.id.goalTextInput).setText(goals[0])

                    }
                } catch (ex: Exception) {
                    println("Error getting goal");
                }

            })
        }).start();

        updateBtn.setOnClickListener{view ->
            val goal = goalTextInput.text.toString()
            val frequencyGoal = frequencyGoalT.text.toString()
            val costGoal = costGoalT.text.toString()
            Thread(Runnable {
                UserServer.pushGoal(goal + "&&&&" + frequencyGoal + "&&&&" + costGoal, fun(res) {
                    if(res.isSuccessful) {
                        println("Updated goal!");
                    }
                })
            }).start()

        }
    }
}
