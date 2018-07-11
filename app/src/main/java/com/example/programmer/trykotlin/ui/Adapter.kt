package com.example.programmer.trykotlin.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel

open class Adapter(val context: Context, var list: List<UserModel>) : RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
    {
//        println("onCreateViewHolder")
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        println("onBindViewHolder")
        holder.bind(list[position])
//        holder.mText.text = list[position].login
    }

    fun filter(query: String) {
        println("filter")
        list = list.filter { it.login.toLowerCase().contains(query.toLowerCase()) }
        notifyDataSetChanged()

        list.forEach{ println(it.toString())}
    }

}