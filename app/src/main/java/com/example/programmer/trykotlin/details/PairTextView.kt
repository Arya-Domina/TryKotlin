package com.example.programmer.trykotlin.details

import android.content.Context
import android.support.annotation.StringRes
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.programmer.trykotlin.R

class PairTextView(context: Context?) : LinearLayout(context) {

    constructor(context: Context?, @StringRes name: Int, value: String) : this(context) {
        LayoutInflater.from(context).inflate(R.layout.pair, this, true)

        findViewById<TextView>(R.id.denotation).setText(name)
        findViewById<TextView>(R.id.value).text = value
    }

//    fun setDenotation(@StringRes name: Int) {
//        findViewById<TextView>(R.id.denotation).setText(name)
//    }
//    fun setValue(value: String) {
//        findViewById<TextView>(R.id.value).text = value
//    }
//
//    fun set(@StringRes name: Int, value: String) {
//        findViewById<TextView>(R.id.denotation).setText(name)
//        findViewById<TextView>(R.id.value).text = value
//    }

    fun setGravityCenter() {
        this.gravity = Gravity.CENTER_HORIZONTAL
    }

}