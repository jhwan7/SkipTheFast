package com.example.skipthefast

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.skipthefast.Data.UserSurvey
import com.example.skipthefast.com.Card

class DialoguePopup(userInput: UserSurvey): DialogFragment() {

    val emotion = userInput.emotion
    val exercise = userInput.exercise
    val chain = userInput.chain
    val category = userInput.category
    val item = userInput.item
    val price = userInput.price

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Your Summary")
            builder.setMessage("THAHAH")
                .setMessage("" +
                        "\nYou ate at: $chain \n" +
                        "\nWhich serves: $category\n" +
                        "\nYou ordered: $item\n" +
                        "\nIt cost \$$price\n" +
                        "\nYou did $exercise exercise\n" +
                        "\nYou felt ${emotion} afterwards\n"
                )
                .setPositiveButton("Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        // FIRE ZE MISSILES!
                    })
                .setNegativeButton("CANCEL",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}