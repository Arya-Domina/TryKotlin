package com.example.programmer.trykotlin.ui.list

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.programmer.trykotlin.Constants
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel
import com.example.programmer.trykotlin.ui.details.UserDetailsActivity

class UserListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var login: TextView = itemView.findViewById(R.id.login)
    var imageUrl: TextView = itemView.findViewById(R.id.image_string)
//    var name: TextView = itemView.findViewById(R.id.name)
//    private var starsCount: TextView = itemView.findViewById(R.id.stars_count)
//    private var repositoriesCount: TextView = itemView.findViewById(R.id.repositories_count)

//    init {
//        println("init UserListHolder")
//        mText = itemView.findViewById(R.id.text)
//    }

    //TODO bind one User
    fun bind(user: UserModel) {
        login.text = user.login
        imageUrl.text = user.avatarUrl
        itemView.setOnClickListener{
            println("click $user")
//            startActivity(itemView.context, Intent(itemView.context, UserDetailsActivity::class.java).putExtra(Constants.USER, user))
            itemView.context.startActivity(Intent(itemView.context, UserDetailsActivity::class.java).putExtra(Constants.USER, user))
        }
    }

    fun bindOnClick(l: View.OnClickListener) {
        itemView.setOnClickListener(l)
    }
}