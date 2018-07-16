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
import com.example.programmer.trykotlin.App
import com.example.programmer.trykotlin.MainContract
import com.example.programmer.trykotlin.MainPresenter
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel
import com.example.programmer.trykotlin.ui.details.UserDetailsActivity

class MainActivity: AppCompatActivity(), MainContract.View/*, SwipeRefreshLayout.OnRefreshListener*/{

//    private val userRepo = RepoUserModel()
    private var recycler: RecyclerView? = null
    private lateinit var presenter: MainContract.Presenter


    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun showListUsers(listUserModel: List<UserModel>) {
        listUserModel.forEach{ println(it.toString())}
    }

    override fun showOneUser(userModel: UserModel) {
        println(userModel)
        startActivity(Intent(this, UserDetailsActivity::class.java))
    }

    override fun updateList() {
        recycler?.adapter = UserListAdapter(this, presenter.getRepo())
//        recycler?.adapter = UserListAdapter(this, presenter.getList())
    }

    override fun start() {
        recycler?.adapter = UserListAdapter(this, presenter.getRepo())
//        recycler?.adapter = UserListAdapter(this, presenter.getList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        println("onCreate")
        App.initApi()

        recycler = findViewById(R.id.recycler_view)
        recycler?.layoutManager = LinearLayoutManager(this)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)

        findViewById<Button>(R.id.button_p).setOnClickListener {
//            userRepo.requestAllUsers()
//            recycler?.adapter = UserListAdapter(this, userRepo.getAllUsers()) //?
            presenter.request()
            // TODO update UI after update data, delay
        }
        findViewById<Button>(R.id.button_f).setOnClickListener {
            presenter.update()
        }
        setPresenter(MainPresenter(this))
        presenter.start()

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
//                recycler?.adapter = UserListAdapter(this@MainActivity, userRepo.getAllUsers()) //?
                presenter.update()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("onQueryTextChange $newText")

//                (recycler?.adapter as UserListAdapter).list = userRepo.getAllUsers()
//                        .filter { it.login.toLowerCase().contains(newText.toLowerCase()) }
//                recycler?.adapter?.notifyDataSetChanged()
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}
