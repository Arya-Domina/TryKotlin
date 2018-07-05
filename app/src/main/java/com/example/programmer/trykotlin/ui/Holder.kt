package com.example.programmer.trykotlin.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.User

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    var mText: TextView
    var login: TextView = itemView.findViewById(R.id.login)
    var name: TextView = itemView.findViewById(R.id.name)
    private var starsCount: TextView = itemView.findViewById(R.id.stars_count)
    private var repositoriesCount: TextView = itemView.findViewById(R.id.repositories_count)

//    init {
//        println("init Holder")
//        mText = itemView.findViewById(R.id.text)
//    }

    fun bind(user: User) {
//        println("bind Holder")
//        mText.text = user.toString()
        login.text = user.login
        name.text = user.name
        starsCount.text = user.starsCount.toString()
        repositoriesCount.text = user.repositories?.size.toString()
    }


}