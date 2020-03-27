package com.example.skipthefast.com

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.R
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat


class Card(context: Context, userSurvey: UserSurvey) {
    private val context = context
    private val userSurvey = userSurvey

    fun getUserData(): UserSurvey {
        return userSurvey
    }

    fun getCard(): MaterialCardView {

        val card = MaterialCardView(context)
        val parentContainer = LinearLayout(context)
        val childContainer = LinearLayout(context)
        val dateText = TextView(context)
        val titleText = TextView(context)
        val bodyText = TextView(context)

        // Food, emotion, exercise
        val foodImage = ImageView(context)
        val emotionImage = ImageView(context)
        val exerciseImage = ImageView(context)

        val cardParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        cardParams.topMargin = 30

        val parentContainerParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val childContainerParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        childContainerParams.setMargins(50, 20, 0, 0)

        val imageParams = LinearLayout.LayoutParams(
            140, 140
        )
        imageParams.setMargins(20, 20, 20, 20)

        card.layoutParams = cardParams
        card.cardElevation = 8f
        card.radius = 15f

        parentContainer.layoutParams = parentContainerParams
        parentContainer.orientation = LinearLayout.HORIZONTAL

        foodImage.layoutParams = imageParams
        foodImage.adjustViewBounds = true

        if (userSurvey.chain == "MC") {
            foodImage.setImageResource(R.drawable.chain_mcd)
        } else {
            foodImage.setImageResource(R.drawable.chain_bk)
        }

        emotionImage.layoutParams = imageParams
        emotionImage.adjustViewBounds = true

        if (userSurvey.emotion == "happy") {
            emotionImage.setImageResource(R.drawable.emotion_happy)
        } else if (userSurvey.emotion == "glad") {
            emotionImage.setImageResource(R.drawable.emotion_smile)
        } else if (userSurvey.emotion == "meh") {
            emotionImage.setImageResource(R.drawable.emotion_meh)
        } else {
            emotionImage.setImageResource(R.drawable.emotion_sad)
        }

        exerciseImage.layoutParams = imageParams
        exerciseImage.adjustViewBounds = true

        if (userSurvey.exercise == "run") {
            exerciseImage.setImageResource(R.drawable.exercise_run)
        } else if (userSurvey.exercise == "walk") {
            exerciseImage.setImageResource(R.drawable.exercise_walk)
        } else if (userSurvey.exercise == "bike") {
            exerciseImage.setImageResource(R.drawable.exercise_bike)
        } else if (userSurvey.exercise == "breathe") {
            exerciseImage.setImageResource(R.drawable.exercise_breathe)
        } else {
            exerciseImage.setImageResource(R.drawable.exercise_run)
        }

        childContainer.layoutParams = childContainerParams
        childContainer.orientation = LinearLayout.VERTICAL

        val textParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dateText.layoutParams = textParams
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        dateText.text = formatter.format(userSurvey.date)
        dateText.setTextColor(Color.parseColor("#0F0101"))
        dateText.textSize = 10f

        titleText.layoutParams = textParams
        titleText.text = "Daily Summary"
        titleText.setTypeface(titleText.typeface, Typeface.BOLD)
        titleText.setTextColor(Color.parseColor("#0F0101"))
        titleText.textSize = 16f

        childContainer.addView(dateText)
        childContainer.addView(titleText)
        childContainer.addView(bodyText)

        parentContainer.addView(foodImage)
        parentContainer.addView(emotionImage)
        parentContainer.addView(exerciseImage)
        parentContainer.addView(childContainer)
        card.addView(parentContainer)

        return card
    }
}