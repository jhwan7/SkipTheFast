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

    constructor(data: JSONObject, date: Date) {
        data.keys().forEach {
            if (it == "Food Chain") {
                chain = data.get(it).toString()
            } else if (it == "Feeling") {
                emotion = data.get(it).toString()
            } else if (it == "Price") {
                price = data.get(it).toString().toFloat()
            } else if (it == "Exercise") {
                exercise = data.get(it).toString()
            } else if (it == "Category") {
                category = data.get(it).toString()
            } else if (it == "Item") {
                item = data.get(it).toString()
            }
        }
        this.date = date
    }

    constructor() {}
}