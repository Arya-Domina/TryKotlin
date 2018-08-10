package com.example.programmer.trykotlin.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel
import com.example.programmer.trykotlin.util.AlertDialogHelper
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.squareup.picasso.Picasso

@SuppressLint("WrongViewCast")
class MainListActivity : AppCompatActivity(), UserListContract.View {

    private val recycler: RecyclerView by lazy {
        return@lazy findViewById<RecyclerView>(R.id.recycler_view)
    }
    private val refreshLayout: SwipyRefreshLayout by lazy {
        return@lazy findViewById<SwipyRefreshLayout>(R.id.swipe_layout)
    }
    private val emptyTextView: TextView by lazy {
        return@lazy findViewById<TextView>(R.id.empty_text_view) //TODO but it is working
    }
    private val presenter: UserListContract.Presenter by lazy {
        return@lazy UserListPresenter(this)
    }

    override fun getView(): View {
        return recycler
    }

    var isSearchMode = false

    override fun showListUsersSearch(listUserModel: List<UserModel>, textIfEmpty: Int) {
        if (listUserModel.isEmpty()) {
            setEmpty(R.string.not_found)
        } else {
            addNewUsers(listUserModel)
        }
        refreshLayout.isRefreshing = false
    }

    override fun setEmpty(textIfEmpty: Int) {
        recycler.adapter = UserListAdapter(listOf())
        recycler.visibility = View.GONE
        emptyTextView.visibility = View.VISIBLE
        emptyTextView.setText(textIfEmpty)
    }

    override fun addNewUsers(newUserList: List<UserModel>) {
        if (newUserList.isNotEmpty()) {
            emptyTextView.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            setScrollListener()
            (recycler.adapter as UserListAdapter).addNewUsers(newUserList)
        }
        refreshLayout.isRefreshing = false
    }

    override fun stopRefreshing() {
        refreshLayout.isRefreshing = false
    }

    override fun clean() {
        recycler.adapter = UserListAdapter(listOf())
        recycler.adapter.notifyDataSetChanged()
    }

    private fun setScrollListener() {
        println("setScrollListener")

        recycler.removeOnScrollListener(listener)
        recycler.addOnScrollListener(listener)
    }

    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
//            println("dx $dx, dy $dy")
            refresh()
        }
    }

    private fun refresh() {
        val lastItemPosition = (recycler.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (lastItemPosition > recycler.adapter.itemCount - 3) {
            println("refresh onScrolled get")
            if (isSearchMode)
                presenter.search()
            else
                presenter.requestNewUsers()
            recycler.removeOnScrollListener(listener)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        println("onCreate")
        Picasso.get().setIndicatorsEnabled(true)
        start()
        setEmpty(R.string.no_access)

        recycler.layoutManager = LinearLayoutManager(this)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)
        refreshLayout.setColorSchemeResources(R.color.refresh_progress_bar_1, R.color.refresh_progress_bar_2, R.color.refresh_progress_bar_3)
        refreshLayout.setOnRefreshListener {
            // set, where was error
            Handler().postDelayed({
                refresh()
            }, 2100)
        }
        presenter.requestNewUsers() //if not null, get current
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            println("onOptionsItemSelected search")
            true
        }
        R.id.action_settings_logging -> {
            println("logging something")
            true
        }
        R.id.action_settings_set_token -> {
            println("set access token")
            val helper = AlertDialogHelper()
            helper.showGetterToken(this)
            true
        }
        R.id.action_settings_get_token -> {
            println("get access token")
            val helper = AlertDialogHelper()
            helper.showShowerToken(this)
            true
        }
        else -> {
            println("onOptionsItemSelected else")
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                println("onMenuItemActionExpand")
                isSearchMode = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                println("onMenuItemActionCollapse")
                isSearchMode = false
                clean()
                setEmpty(R.string.no_access)
                addNewUsers(presenter.getCurrentUsers())
                return true
            }
        })

        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                println("onQueryTextSubmit $query")
                presenter.search(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                println("onQueryTextChange $newText")
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}
