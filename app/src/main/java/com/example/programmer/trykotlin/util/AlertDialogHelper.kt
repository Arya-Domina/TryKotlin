package com.example.programmer.trykotlin.util

import android.app.AlertDialog
import android.content.Context
import android.preference.PreferenceManager
import android.widget.EditText
import android.widget.TextView
import com.example.programmer.trykotlin.App
import com.example.programmer.trykotlin.Constants
import com.example.programmer.trykotlin.R

class AlertDialogHelper {

    fun showGetterToken(context: Context) {
        val editText = EditText(context)
//        editText.setPadding(50, 20, 50, 0)
//        editText.setBackgroundColor(context.resources.getColor(R.color.swipe_layout_background))
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.enter_token_title))
                .setMessage(context.getString(R.string.enter_token_message)) // such as user email
                .setView(editText)
                .setPositiveButton(context.getString(R.string.save), { dialog, which ->
                    println("showGetterToken, inputting text: ${editText.text}")
                    PreferenceManager.getDefaultSharedPreferences(App.context).edit()
                            .putString(Constants.HEADER_VALUE, editText.text.toString()).apply()
                })
                .show()
    }


    fun showShowerToken(context: Context) {
        val textView = TextView(context)
        textView.setTextIsSelectable(true)
        textView.setPadding(50, 20, 50, 0)
        textView.text = PreferenceManager.getDefaultSharedPreferences(App.context).getString(Constants.HEADER_VALUE, "")
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.current_token_title))
                .setView(textView)
                .setNeutralButton(context.getString(R.string.ok), { _, _ -> })
                .show()
    }

}