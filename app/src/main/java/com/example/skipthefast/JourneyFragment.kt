package com.example.skipthefast

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.ServerConnection.UserServer
import com.example.skipthefast.com.Card
import kotlinx.android.synthetic.main.fragment_journey.*
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [JourneyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JourneyFragment : Fragment(), MainActivity.ListenFromActivity {
    private val userInputs: MutableList<UserSurvey> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setActivityListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journey, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!UserServer.isTest) {
            Thread(Runnable {
                UserServer.getData(fun(res) {
                    val data = JSONObject(res.body()!!.string())

                    data.keys().forEachRemaining { dateStr ->
                        run {
                            val date: Date = Date.from(
                                LocalDateTime.parse(
                                    dateStr,
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                ).atZone(ZoneId.systemDefault()).toInstant()
                            )
                            val userInput = UserSurvey(data.get(dateStr) as JSONObject, date)
                            activity!!.runOnUiThread(java.lang.Runnable {
                                populateCard(userInput, false)
                            }
                            )
                        }
                    }
                    /*calculateWeeklySpending()*/
                })
            }).start()
        }
    }

/*    fun calculateWeeklySpending() {
        var costSum = 0f
        var freqCount = 0;

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)

        userInputs.forEach {
            if (it.date > calendar.time) {
                costSum += it.price
                freqCount++
            }
        }
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    override fun populateCard(userInput: UserSurvey, updateDB: Boolean) {
        cardsLayout.invalidate()
        userInputs.add(userInput)
        cardsLayout.addView(createCard(userInput))

        if (updateDB) {
            UserServer.pushData(userInput, fun(res) {
                if (res.isSuccessful) {
                    println("Updated db!")
                }
                else {
                    println("Update failed")
                }
            })
        }

        totalUserEntry.text = userInputs.size.toString() + " entries total"
    }

    fun createCard(userInput: UserSurvey): View {
        val newCard = Card(context!!, userInput).getCard()
        newCard.setOnClickListener {
            val dialoguePopup = DialoguePopup(userInput)
            dialoguePopup.show(activity!!.supportFragmentManager, "")
        }
        return newCard
    }
}
