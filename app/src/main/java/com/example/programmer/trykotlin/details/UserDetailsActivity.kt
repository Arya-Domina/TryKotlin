package com.example.programmer.trykotlin.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.programmer.trykotlin.Constants.Companion.USER
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel
import com.squareup.picasso.Picasso

class UserDetailsActivity : AppCompatActivity(), UserDetailsContract.View {

    private val presenter: UserDetailsPresenter by lazy {
        UserDetailsPresenter(this)
    }
    private val layout by lazy {
        findViewById<LinearLayout>(R.id.list)
    }
    private val imageView by lazy {
        findViewById<ImageView>(R.id.image)
    }

    override fun getView(): View {
        return layout
    }

    private fun makeMapFromUser(userModel: UserModel) : Map<Int, String> {
        val map = hashMapOf<Int, String>()

        userModel.login?.let { map.put(R.string.login, it) }
        if (userModel.hasDetails) {
            map[R.string.name] = userModel.name ?: "no named"
//        userModel.name?.let { map.put(R.string.name, it) }
            userModel.email?.let { map.put(R.string.email, it) }
            userModel.company?.let { map.put(R.string.company, it) }
            userModel.repositoriesCount?.let { map.put(R.string.repos, it.toString()) }
        }

        return map
    }

    override fun bindUsver(user: UserModel) {
        println("bindUser")
        layout.removeAllViews()
        for ((key, value) in makeMapFromUser(user))
            layout.addView(PairTextView(this, key, value))

        Picasso.get().load(user.avatarUrl).fit().placeholder(R.drawable.icon).error(R.drawable.error).into(imageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)
        println("UserDetailsActivity onCreate")
        start()

        val username = intent.getSerializableExtra(USER) as String?
        println("UserDetailsActivity onCreate username $username")
        username?.let { presenter.getUsver(it) } ?: println("no login")

    }
}