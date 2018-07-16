package com.example.programmer.trykotlin.ui.list

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.programmer.trykotlin.Constants
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.ui.details.UserDetailsActivity

open class UserListAdapter(private val context: Context, var repoUserModel: RepoUserModel) : RecyclerView.Adapter<UserListHolder>() {

//    constructor(context: Context, list: List<UserModel>, repoUserModel: RepoUserModel) : this(context, list) {
//        this.repoUserModel = repoUserModel
//    }
//
//    lateinit var repoUserModel: RepoUserModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder
    {
//        println("onCreateViewHolder")
        return UserListHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false))
    }

    override fun getItemCount(): Int = repoUserModel.getAllUsers().size

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
//        println("onBindViewHolder")
        holder.bind(repoUserModel.getAllUsers()[position])
        holder.bindOnClick(View.OnClickListener {
//            if (repoUserModel.getAllUsers()[position].id != null) {
                val i = repoUserModel.getAllUsers()[position].id as Int
                val user = repoUserModel.getUserById(i)

                context.startActivity(Intent(context, UserDetailsActivity::class.java).putExtra(Constants.USER, user))
//            }
        })
//        holder.mText.text = list[position].login
    }

//    fun filter(query: String) {
//        println("filter")
//        list = list.filter { it.login.toLowerCase().contains(query.toLowerCase()) }
//        notifyDataSetChanged()
//
//        list.forEach{ println(it.toString())}
//    }

}