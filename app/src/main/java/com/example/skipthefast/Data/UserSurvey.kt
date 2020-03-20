package com.example.skipthefast.Data

import org.json.JSONObject
import java.util.*

class UserSurvey{
    public var chain: String = ""
    public var category: String = ""
    public var item: String = ""
    public var price: Float = 0f
    public var emotion: String = ""
    public var exercise: String = ""
    public var date: Date = Date()

    constructor(data: JSONObject) {
        println("hello!")
        data.keys().forEach {
            val surveyData = (data.get(it) as JSONObject)
            surveyData.keys().forEach {
                if (it == "chain") {
                    chain = surveyData.get(it).toString()
                } else if (it == "emotion") {
                    emotion = surveyData.get(it).toString()
                } else if (it == "exercise") {
                    exercise = surveyData.get(it).toString()
                }
            }
        }
    }
    fun emotionToNum(): Int {
        when(emotion){
            "happy" -> return 5
            "glad" -> return 4
            "meh" -> return 3
            "sad" -> return 2
            "miserable" -> return 1
            else -> {
                throw Exception("Unknown emotion type")
            }
        }
    }
    constructor() {}
}