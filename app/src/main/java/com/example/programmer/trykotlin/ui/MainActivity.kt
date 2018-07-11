package com.example.programmer.trykotlin.ui;

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
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.Repo
import com.example.programmer.trykotlin.model.RepoUserModel
import com.example.programmer.trykotlin.model.User

class MainActivity: AppCompatActivity(), BaseView/*, SwipeRefreshLayout.OnRefreshListener*/{

    private val repo = Repo()
    private val userRepo = RepoUserModel()
    private var recycler: RecyclerView? = null


    override fun showAllUsers() {
        userRepo.getAllUsers().forEach{ println(it.toString())}
    }

    override fun showOneUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        println("onCreate")
        App.initApi()

//        var mPresenter: BasePresenter<MainActivity>
        recycler = findViewById(R.id.recycler_view)

        repo.createNewUsers()
        recycler?.layoutManager = LinearLayoutManager(this)
        recycler?.adapter = Adapter(this, userRepo.getAllUsers())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)

        findViewById<Button>(R.id.button_p).setOnClickListener {

        userRepo.requestAllUsers()
        recycler?.adapter = Adapter(this, userRepo.getAllUsers()) //?
        // TODO update UI after update data, delay

        }
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

                userRepo.requestSearch(query)
                recycler?.adapter = Adapter(this@MainActivity, userRepo.getAllUsers()) //?

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("onQueryTextChange $newText")

                (recycler?.adapter as Adapter).list = userRepo.getAllUsers()
                        .filter { it.login.toLowerCase().contains(newText.toLowerCase()) }
                recycler?.adapter?.notifyDataSetChanged()
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}
