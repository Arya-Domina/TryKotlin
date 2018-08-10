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
            holder.bind(list[position], position)

//    fun addNewTestUsers() {
//        val last = list.size
//        val newList = list.toMutableList()
//        newList.add(UserModel("Test"))
//        newList.add(UserModel("Test2"))
//        newList.add(UserModel("TT3"))
//        list = newList.toList()
//        notifyItemRangeInserted(last, 5)
//    }

    fun addNewUsers(newList: List<UserModel>) {
        val last = list.size
        val l = list.toMutableList()
        newList.forEach({l.add(it)})
        list = l.toList()
        notifyItemRangeInserted(last, newList.size)
    }

}