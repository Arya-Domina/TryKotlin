package com.example.programmer.trykotlin.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.programmer.trykotlin.Constants.Companion.USER
import com.example.programmer.trykotlin.R
import com.example.programmer.trykotlin.model.RepoModel
import com.example.programmer.trykotlin.model.UserModel
import com.jakewharton.rxbinding2.view.RxView
import com.squareup.picasso.Picasso

class UserDetailsActivity : AppCompatActivity(), UserDetailsContract.View {

    private val presenter: UserDetailsPresenter by lazy {
        UserDetailsPresenter(this)
    }
    private val infoList by lazy {
        findViewById<LinearLayout>(R.id.info_list)
    }
    private val repoListView by lazy {
        findViewById<RecyclerView>(R.id.repo_list)
    }
    private val imageView by lazy {
        findViewById<ImageView>(R.id.image)
    }
    private val buttonRepo: Button by lazy {
        findViewById<Button>(R.id.repo_button)
    }
    private val buttonInfo: Button by lazy {
        findViewById<Button>(R.id.info_button)
    }
    private var isShownInfo = true

    override fun getView(): View {
        return infoList
    }

    private fun LinearLayout.add(resId: Int, value: String?) {
        value?.let { this.addView(PairTextView(this.context, resId, it)) }
    }

    private fun LinearLayout.add(resId: Int, value: Int?) {
        this.add(resId, value.toString())
    }

    override fun bindUser(user: UserModel){
        println("bindUser $user")
        infoList.removeAllViews()
        Picasso.get().load(user.avatarUrl).fit().placeholder(R.drawable.icon_placeholder).error(R.drawable.icon_error).into(imageView)
        infoList.add(R.string.login, user.login)
    }

    override fun bindUserInfo(user: UserModel) {
        println("bindUserInfo $user")
        infoList.removeAllViews()
        repoListView.visibility = View.GONE

        infoList.add(R.string.login, user.login)
//        add(R.string.login, user.login)
        if (user.hasDetails) {
            infoList.add(R.string.name, user.name)
            infoList.add(R.string.location, user.location)
            infoList.add(R.string.company, user.company)
            infoList.add(R.string.email, user.email)
            infoList.add(R.string.repos, user.repositoriesCount)
        } else {
            infoList.add(R.string.not_user, "details")
        }
    }

    override fun bindUserRepo(repoList: List<RepoModel>) {
        println("bindUserRepo size: ${repoList.size}")
        println("$repoList")
        infoList.removeAllViews()
        infoList.add(R.string.login, repoList[0].user?.login)
        repoListView.visibility = View.VISIBLE

        repoListView.layoutManager = LinearLayoutManager(this)
        repoListView.adapter = Adapter(repoList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details)
        println("UserDetailsActivity onCreate")
        start()

        val username = intent.getSerializableExtra(USER) as String?
        println("UserDetailsActivity onCreate username $username")
        username?.let { presenter.getUser(it) } ?: println("no login")

        RxView.clicks(buttonInfo)
                .subscribe({
                    username?.let { presenter.getUserInfo(it) } ?: println("no login")
        })

        RxView.clicks(buttonRepo)
                .subscribe({
                    username?.let { presenter.getUserRepos(it) } ?: println("no login")
                })

    }
}