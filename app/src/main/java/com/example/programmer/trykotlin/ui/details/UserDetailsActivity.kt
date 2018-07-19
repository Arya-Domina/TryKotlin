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
        println("UserDetailsActivity onCreate")
        val user = intent.getSerializableExtra(USER) as UserModel?
        println(user)

        val layout = findViewById<LinearLayout>(R.id.list)
//        if (user != null)
//            for ((key, value) in makeMapFromUser(user)) {
//                layout.addView(PairTextView(this, key, value))
//            }
        user?.let { for ((key, value) in makeMapFromUser(it))
            layout.addView(PairTextView(this, key, value))}
                ?: println("no user is UserDetailsActivity")

    }
}