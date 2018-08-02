package com.example.programmer.trykotlin.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.UserModel
import com.squareup.picasso.Picasso

@SuppressLint("WrongViewCast")
class MainListActivity : AppCompatActivity(), UserListContract.View {

    private val recycler: RecyclerView by lazy {
        return@lazy findViewById<RecyclerView>(R.id.recycler_view)
    }
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        return@lazy findViewById<SwipeRefreshLayout>(R.id.swipe_layout)
    }
    private val emptyTextView: TextView by lazy {
        return@lazy findViewById<TextView>(R.id.empty_text_view) //TODO but it is working
    }
    private val presenter: UserListContract.Presenter by lazy {
        return@lazy UserListPresenter(this)
    }
    private val currentPageButton: Button by lazy {
        return@lazy findViewById<Button>(R.id.current_page)
    }
    private val buttonsBar: LinearLayout by lazy {
        return@lazy findViewById<LinearLayout>(R.id.button_bar)
    }

    private var currentPage: Int = 0
    private var currentLastPage: Int = 0
    private var searchMode: Boolean = false

    override fun getView(): View {
        return recycler
    }

    override fun showListUsers(listUserModel: List<UserModel>, textIfEmpty: String) {
        recycler.adapter = UserListAdapter(listUserModel)
        if (recycler.adapter.itemCount == 0) {
            recycler.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
            emptyTextView.text = textIfEmpty
        } else {
            recycler.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showListUsers(listUserModel: List<UserModel>, textIfEmpty: Int) {
        recycler.adapter = UserListAdapter(listUserModel)
        if (recycler.adapter.itemCount == 0) {
            recycler.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
            emptyTextView.setText(textIfEmpty)
        } else {
            recycler.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE
        }
        swipeRefreshLayout.isRefreshing = false
    }

    override fun stopRefreshing() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun turnOnSearchMode() {
        searchMode = true
        buttonsBar.visibility = View.VISIBLE
        swipeRefreshLayout.isEnabled = false
    }

    override fun turnOffSearchMode() {
        searchMode = false
        buttonsBar.visibility = View.GONE
        swipeRefreshLayout.isEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        println("onCreate")
        Picasso.get().setIndicatorsEnabled(true)
        start()
        turnOffSearchMode()

        recycler.layoutManager = LinearLayoutManager(this)
        showListUsers(listOf(), "Нет доступа")
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_bar_1, R.color.refresh_progress_bar_2, R.color.refresh_progress_bar_3)
        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({ presenter.requestUsers() }, 2100)
        }

        presenter.requestUsers()
    }

    override fun setPage(page: Int, lastPage: Int) {
        currentPage = page
        currentLastPage = lastPage
    }

    override fun bindButtonsOnClick() {
        findViewById<Button>(R.id.first).setOnClickListener {
            println("first")
            currentPage = 1
            presenter.searchUsersByLastQuery(1)
        }
        findViewById<Button>(R.id.prev).setOnClickListener {
            println("prev")
            presenter.searchUsersByLastQuery(--currentPage)
        }
        findViewById<Button>(R.id.next).setOnClickListener {
            println("next")
            presenter.searchUsersByLastQuery(++currentPage)
        }
        findViewById<Button>(R.id.last).setOnClickListener {
            println("last")
            currentPage = currentLastPage
            presenter.searchUsersByLastQuery(currentLastPage)
        }
    }

    override fun bindButtonsClickable() {
        currentPageButton.text = currentPage.toString()
        if (currentPage == 1) {
            findViewById<Button>(R.id.first).isClickable = false
            findViewById<Button>(R.id.prev).isClickable = false
        } else {
            findViewById<Button>(R.id.first).isClickable = true
            findViewById<Button>(R.id.prev).isClickable = true
        }
        if (currentPage == currentLastPage) {
            findViewById<Button>(R.id.last).isClickable = false
            findViewById<Button>(R.id.next).isClickable = false
        } else {
            findViewById<Button>(R.id.last).isClickable = true
            findViewById<Button>(R.id.next).isClickable = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            println("onOptionsItemSelected search")
            true
        }
        R.id.action_settings_one -> {
            println("onOptionsItemSelected one")
            println("Query: ${presenter.getCurrentQueryForLog()}, page: $currentPage, lasPage: $currentLastPage")
            true
        }
        R.id.action_settings_two -> {
            println("onOptionsItemSelected two")
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
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                println("onMenuItemActionCollapse")
                presenter.requestUsers()
                return true
            }
        })

        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newQuery: String): Boolean {
                println("onQueryTextSubmit $newQuery")
                presenter.searchUsers(newQuery)
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
