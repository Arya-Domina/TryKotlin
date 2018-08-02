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
    private val firstButton: Button by lazy {
        return@lazy findViewById<Button>(R.id.first)
    }
    private val prevButton: Button by lazy {
        return@lazy findViewById<Button>(R.id.prev)
    }
    private val nextButton: Button by lazy {
        return@lazy findViewById<Button>(R.id.next)
    }
    private val lastButton: Button by lazy {
        return@lazy findViewById<Button>(R.id.last)
    }
    private val buttonsBar: LinearLayout by lazy {
        return@lazy findViewById<LinearLayout>(R.id.button_bar)
    }

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
        buttonsBar.visibility = View.VISIBLE
        swipeRefreshLayout.isEnabled = false
    }

    override fun turnOffSearchMode() {
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

    override fun setButtons(page: Int, lastPage: Int) {
        firstButton.setOnClickListener {
            println("first")
            presenter.searchUsers(page = 1)
        }
        prevButton.setOnClickListener {
            println("prev")
            presenter.searchUsers(page = page - 1)
        }
        nextButton.setOnClickListener {
            println("next")
            presenter.searchUsers(page = page + 1)
        }
        lastButton.setOnClickListener {
            println("last")
            presenter.searchUsers(page = lastPage)
        }

        currentPageButton.text = page.toString()

        if (page == 1) {
            firstButton.isClickable = false
            prevButton.isClickable = false
        } else {
            firstButton.isClickable = true
            prevButton.isClickable = true
        }
        if (page == lastPage) {
            lastButton.isClickable = false
            nextButton.isClickable = false
        } else {
            lastButton.isClickable = true
            nextButton.isClickable = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            println("onOptionsItemSelected search")
            true
        }
        R.id.action_settings_one -> {
            println("onOptionsItemSelected one")
            println("Query: ${presenter.getCurrentQueryForLog()}, " +
                    "page: ${currentPageButton.text}")
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
