package com.example.programmer.trykotlin.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun makeMapFromUser(userModel: UserModel) : Map<Int, String> {
        val map = hashMapOf<Int, String>()

        userModel.login?.let { map.put(R.string.login, it) }
        map[R.string.name] = userModel.name ?: "no named"
//        userModel.name?.let { map.put(R.string.name, it) }
        userModel.email?.let { map.put(R.string.email, it) }
        userModel.company?.let { map.put(R.string.company, it) }
        userModel.repositoriesCount?.let { map.put(R.string.repos, it.toString()) }

        return map
    }

    override fun bindUsver(user: UserModel?) {
        user?.let {
            for ((key, value) in makeMapFromUser(it))
                layout.addView(PairTextView(this, key, value))

            Picasso.get().load(user.avatarUrl).fit().placeholder(R.drawable.icon).error(R.drawable.error).into(imageView)
        }
                ?: println("no user is UserDetailsActivity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)
        println("UserDetailsActivity onCreate")

//        presenter.start()

        val user = intent.getSerializableExtra(USER) as UserModel?
        println("UserDetailsActivity onCreate user $user")

        if (user?.hasDetails == true) {
            println("has details")
            bindUsver(user)
        } else
            user?.login?.let {
                presenter.getUsver(it)
            } ?: println("no user or login")

    }
}