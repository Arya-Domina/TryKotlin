package com.example.programmer.trykotlin.ui;

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.programmer.trykotlin.BaseView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.Repo
import com.example.programmer.trykotlin.model.User

class MainActivity: AppCompatActivity(), BaseView/*, SwipeRefreshLayout.OnRefreshListener*/{

    private val repo = Repo()
    private var recycler: RecyclerView? = null
    private var i = 0;

    override fun showAllUsers() {
        repo.getAllUsers().forEach{ println(it.toString())}
    }

    private fun addCloneOfUser() {
        repo.addUser(User("Clone of Vasy ${++i}", "Last", "@mail", 42, listOf("company", "other company"), listOf("repository", "new repository")))
        recycler?.adapter?.notifyDataSetChanged()
    }

    fun addUser(user: User) {
        repo.addUser(user)
        recycler?.adapter?.notifyDataSetChanged()
    }

    override fun showOneUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        println("onCreate")

//        var mPresenter: BasePresenter<MainActivity>
        recycler = findViewById(R.id.recycler_view)

        repo.createNewUsers()
        recycler?.layoutManager = LinearLayoutManager(this)
        recycler?.adapter = Adapter(this, repo.getAllUsers())
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)

        findViewById<Button>(R.id.button_p).setOnClickListener{addCloneOfUser(); showAllUsers()}

//        val user = User("Vasy", "First", "@mail", 42, listOf("company", "other company"), listOf("repository", "new repository"))
//        user.name = "Vasy"
//        user.login = "First"
//        user.email = "@mail"
//        user.starsCount = 42
//        user.companies = listOf("company", "other company")
//        user.repositories = listOf("repository", "new repository")

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
//                recycler?.adapter = Adapter(this@MainActivity, repo.getAllUsers())
//                (recycler?.adapter as Adapter).list = repo.getAllUsers()
                println("onQueryTextSubmit $query")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("onQueryTextChange $newText")

                (recycler?.adapter as Adapter).list = repo.getAllUsers()
                        .filter { it.name?.toLowerCase()?.contains(newText.toLowerCase()) ?: false ||
                                it.login?.toLowerCase()?.contains(newText.toLowerCase()) ?: false } as ArrayList<User>
                recycler?.adapter?.notifyDataSetChanged()
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}
