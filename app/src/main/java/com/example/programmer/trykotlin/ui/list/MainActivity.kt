package com.example.programmer.trykotlin.ui.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.programmer.trykotlin.Constants
import com.example.programmer.trykotlin.MainContract
import com.example.programmer.trykotlin.MainPresenter
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel
import com.example.programmer.trykotlin.ui.details.UserDetailsActivity

class MainActivity: AppCompatActivity(), MainContract.View/*, SwipeRefreshLayout.OnRefreshListener*/{

    private val recycler: RecyclerView by lazy {
        return@lazy findViewById<RecyclerView>(R.id.recycler_view) }
    private val presenter: MainContract.Presenter by lazy {
        return@lazy MainPresenter(this) }

    override fun showListUsers(listUserModel: List<UserModel>) {
        listUserModel.forEach{ println(it.toString())}
        recycler.adapter = UserListAdapter(this, listUserModel)
    }

    override fun showOneUser(userModel: UserModel) {
        println("MainActivity showOneUser")
        println(userModel)
        startActivity(Intent(this, UserDetailsActivity::class.java).putExtra(Constants.USER, userModel))
    }

    override fun updateList() {
//        recycler.adapter = UserListAdapter(this, presenter.getRepo())
    }

    override fun start() {
//        recycler.adapter = UserListAdapter(this, presenter.getRepo())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        println("onCreate")

        recycler.layoutManager = LinearLayoutManager(this)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)

        findViewById<Button>(R.id.button_p).setOnClickListener {
//            userRepo.requestAllUsers()
//            recycler?.adapter = UserListAdapter(this, userRepo.getUserList()) //?
            presenter.printUsers()
//            presenter.request()
        }
        findViewById<Button>(R.id.button_f).setOnClickListener {
//            presenter.update()
            println("pusch")
            presenter.getUsver()
        }

        presenter.start()
//        presenter.getAllUsersDetails()

    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            // User chose the "Settings" item, show the app settings UI...
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                println("onQueryTextSubmit $query")

//                userRepo.requestSearch(query)
//                recycler?.adapter = UserListAdapter(this@MainActivity, userRepo.getUserList()) //?
                presenter.update()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("onQueryTextChange $newText")

//                (recycler?.adapter as UserListAdapter).list = userRepo.getUserList()
//                        .filter { it.login.toLowerCase().contains(newText.toLowerCase()) }
//                recycler?.adapter?.notifyDataSetChanged()
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}
