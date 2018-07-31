package com.example.programmer.trykotlin.list

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.programmer.trykotlin.Constants
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.details.PairTextView
import com.example.programmer.trykotlin.details.UserDetailsActivity
import com.example.programmer.trykotlin.model.UserModel
import com.squareup.picasso.Picasso

class UserListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.image)
    private val layout by lazy {
        itemView.findViewById<LinearLayout>(R.id.fields_list)
    }

    fun bind(user: UserModel) {
        println("bind user ${user.login}")

        layout.removeAllViews()
        layout.addView(PairTextView(imageView.context, R.string.login, user.login?.let { it }
                ?: ""))
        layout.addView(PairTextView(imageView.context, R.string.type, user.type?.let { it } ?: ""))

        Picasso.get().load(user.avatarUrl).fit().placeholder(R.drawable.icon_placeholder).error(R.drawable.icon_error).into(imageView)

        itemView.setOnClickListener{
            println("click $user")
            itemView.context.startActivity(Intent(itemView.context, UserDetailsActivity::class.java).putExtra(Constants.USER, user.login))
        }
    }
}