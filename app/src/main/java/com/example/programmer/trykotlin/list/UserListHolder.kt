package com.example.programmer.trykotlin.list

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.programmer.trykotlin.Constants
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.details.UserDetailsActivity
import com.example.programmer.trykotlin.model.UserModel
import com.squareup.picasso.Picasso

class UserListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val login: TextView = itemView.findViewById(R.id.login)
    private val imageUrl: TextView = itemView.findViewById(R.id.image_string)
    private val imageView: ImageView = itemView.findViewById(R.id.image)

    fun bind(user: UserModel) {
        println("bind user ${user.login}")
        login.text = user.login
        imageUrl.text = user.avatarUrl
        Picasso.get().load(user.avatarUrl).fit().placeholder(R.drawable.icon).error(R.drawable.error).into(imageView)
//        Picasso.get().load(if (adapterPosition < 15) {user.avatarUrl} else "qwe").placeholder(R.drawable.icon).error(R.drawable.error).into(imageView)

        itemView.setOnClickListener{
            println("click $user")
            itemView.context.startActivity(Intent(itemView.context, UserDetailsActivity::class.java).putExtra(Constants.USER, user.login))
        }
    }
}