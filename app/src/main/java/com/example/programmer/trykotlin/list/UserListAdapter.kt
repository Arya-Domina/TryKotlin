package com.example.programmer.trykotlin.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel

open class UserListAdapter(var list: List<UserModel>) : RecyclerView.Adapter<UserListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder =
            UserListHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserListHolder, position: Int) =
            holder.bind(list[position])

//    fun filter(query: String) {
//        println("filter")
//        list = list.filter { it.login.toLowerCase().contains(query.toLowerCase()) }
//        notifyDataSetChanged()
//
//        list.forEach{ println(it.toString())}
//    }

}