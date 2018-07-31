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

    private fun add(resId: Int, value: String?) {
        value?.let { layout.addView(PairTextView(this, resId, it)) }
    }

    override fun bindUsver(user: UserModel) {
        println("bindUser $user")
        layout.removeAllViews()

        add(R.string.login, user.login)
        if (user.hasDetails) {
            add(R.string.name, user.name)
            add(R.string.location, user.location)
            add(R.string.company, user.company)
            add(R.string.email, user.email)
            add(R.string.repos, user.repositoriesCount.toString())
        }

        Picasso.get().load(user.avatarUrl).fit().placeholder(R.drawable.icon_placeholder).error(R.drawable.icon_error).into(imageView)
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