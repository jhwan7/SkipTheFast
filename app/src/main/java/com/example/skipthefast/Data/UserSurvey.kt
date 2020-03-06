package com.example.skipthefast.Data

import org.json.JSONObject

class UserSurvey{
    public var chain: String = ""
    public var category: String = ""
    public var item: String = ""
    public var price: Float = 0f
    public var emotion: String = ""
    public var exercise: String = ""

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

    constructor() {}
}