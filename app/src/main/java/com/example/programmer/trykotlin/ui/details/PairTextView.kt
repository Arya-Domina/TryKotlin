package com.example.programmer.trykotlin.ui.details

import android.content.Context
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.programmer.trykotlin.R

class PairTextView(context: Context?) : LinearLayout(context) {

    constructor(context: Context?, @StringRes name: Int, value: String) : this(context) {
//        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.pair, this, true)

        findViewById<TextView>(R.id.denotation).setText(name)
        findViewById<TextView>(R.id.value).text = value
    }

//    fun bind(name: String, value: String) {
//        findViewById<TextView>(R.id.denotation).text = name
//        findViewById<TextView>(R.id.value).text = value
//    }

}