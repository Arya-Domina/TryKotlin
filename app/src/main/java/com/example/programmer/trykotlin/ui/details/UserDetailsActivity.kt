package com.example.programmer.trykotlin.ui.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.example.programmer.trykotlin.Constants.Companion.USER
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel

class UserDetailsActivity: AppCompatActivity() {

//    var login: TextView = findViewById(R.id.login)
//    var name: TextView = findViewById(R.id.name)
//    var email: TextView = findViewById(R.id.email)
//    var company: TextView = findViewById(R.id.company)
//    var repositoriesCount: TextView = findViewById(R.id.repos_count)

    fun bind() {

    }

    private fun makeMapFromUser(userModel: UserModel) : Map<Int, String> {
        val map = hashMapOf<Int, String>()

//        val map = hashMapOf<String, Int>()
//        map.put("one", 1)
//        map.put("two", 2)
//
//        for ((key, value) in map) {
//            println("key = $key, value = $value")
//        }

        userModel.login?.let { map.put(R.string.login, it) }
        map[R.string.name] = userModel.name ?: "no named"
//        userModel.name?.let { map.put(R.string.name, it) }
        userModel.email?.let { map.put(R.string.email, it) }
        userModel.company?.let { map.put(R.string.company, it) }
        userModel.repositoriesCount?.let { map.put(R.string.repos, it.toString()) }

        return map
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)
        val user = intent.getSerializableExtra(USER) as UserModel
        println(user)

        val layout = findViewById<LinearLayout>(R.id.list)

        for ((key, value) in makeMapFromUser(user)) {
            layout.addView(PairTextView(this, key, value))
        }

//        val recyclerView = findViewById<RecyclerView>(R.id.pair_recycler_view)
//        recyclerView.adapter = PairUserDetailsAdapter(this, makeMapFromUser(user))


//        val pairLogin = PairTextView(this, R.string.login, user.login)
//        layout.addView(pairLogin)
//        layout.addView(PairTextView(this, R.string.name, if (user.name != null) user.name else "no name"))
//        layout.addView(PairTextView(this, R.string.email, if (user.email != null) user.email else "no email"))
//        layout.addView(PairTextView(this, R.string.company, if (user.company != null) user.company else "no company"))
//        layout.addView(PairTextView(this, R.string.repos, if (user.repositoriesCount != null) user.repositoriesCount.toString() else "no repos"))


//        val pairName = PairTextView(this, R.string.name, user.name)
//        layout.addView(pairName)
//        val pairEmail = PairTextView(this, R.string.email, user.email)
//        layout.addView(pairEmail)
//        val pairCompany = PairTextView(this, R.string.company, user.company)
//        layout.addView(pairCompany)
//        val pairRepos = PairTextView(this, R.string.repos, user.repositoriesCount.toString())
//        layout.addView(pairRepos)


//        layout.setOnClickListener{}
    }
}